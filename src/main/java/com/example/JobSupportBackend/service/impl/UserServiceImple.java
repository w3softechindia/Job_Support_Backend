package com.example.JobSupportBackend.service.impl;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.JobSupportBackend.EmailUtil.EmailUtil;
import com.example.JobSupportBackend.EmailUtil.OtpUtil;
import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.UserService;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OtpUtil otpUtil;
	
	@Autowired
	private EmailUtil emailUtil;

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public User register(Register register) throws InvalidIdException, MessagingException {
		Optional<User> user2 = repo.findById(register.getEmail());
		if(user2.isPresent()) {
			throw new InvalidIdException("Email already exists...!!!"+register.getEmail());
		}
		else {
			String otp=otpUtil.generateOtp();
			emailUtil.sendOtpMail(register.getEmail(), otp);
			User user = User.builder().username(register.getUsername()).email(register.getEmail())
					.password(getEncodedPassword(register.getPassword()))
					.otp(otp)
					.otpGeneratedtime(LocalDateTime.now())
					.build();
			return repo.save(user);
		}
	}

	@Override
	public User updateRole(String email,String newRole) throws Exception {
		User uuser = repo.findById(email).orElseThrow(() -> new Exception("Email Id not found..!!!"));
		if(uuser!=null) {
			uuser.setRole(newRole);
			return repo.save(uuser);
		}else {
			throw new InvalidIdException("Email not found..!!!"+email);
		}
	}

	@Override
	public User updatePersonalInfo(PersonalInfo personalInfo, String email) throws Exception {
		User uuser = repo.findById(email).orElseThrow(() -> new InvalidIdException("Email Id not found..!!!"));
		uuser.setFirstname(personalInfo.getFirstname());
		uuser.setLastname(personalInfo.getLastname());
		uuser.setPhonenumber(personalInfo.getPhonenumber());
		uuser.setDob(personalInfo.getDob());
		uuser.setJobtitle(personalInfo.getJobtitle());
		uuser.setTypeofjob(personalInfo.getTypeofjob());
		uuser.setDescription(personalInfo.getDescription());

		return repo.save(uuser);
	}

	@Override
	public User otherinfo(Otherinfo otherInfo, String email) throws Exception {
		User user = repo.findById(email).orElseThrow(() -> new InvalidIdException("Email Id not found..!!!"));
		user.setFacebook(otherInfo.getFacebook());
		user.setInstagram(otherInfo.getInstagram());
		user.setLinkedin(otherInfo.getLinkedin());
		user.setPersnolurl(otherInfo.getPersnolurl());
		user.setAddress(otherInfo.getAddress());
		user.setCity(otherInfo.getCity());
		user.setState(otherInfo.getState());
		user.setPostcode(otherInfo.getPostcode());
		user.setDocumenttype(otherInfo.getDocumenttype());
		user.setDocumentnumber(otherInfo.getDocumentnumber());
		return repo.save(user);
	}

	@Override
	public User getDetails(String email) {
		User user = repo.findByEmail(email);
		System.out.println(user);
		return user;
	}

	@Override
	public User employerInfo(EmployerInfo employerInfo, String email) throws InvalidIdException {
		User user = repo.findById(email).orElseThrow(()->new InvalidIdException("Email Id not found..!!!"));
		user.setEcompany(employerInfo.getEcompany());
		user.setEtagline(employerInfo.getEtagline());
		user.setEstablishdate(employerInfo.getEstablishdate());
		user.setEcompanyownername(employerInfo.getEcompanyownername());
		user.setIndustry(employerInfo.getIndustry());
		user.setEwebsite(employerInfo.getEwebsite());
		user.setEteamsize(employerInfo.getEteamsize());
		user.setEdescribe(employerInfo.getEdescribe());
		return repo.save(user);
	}

	@Override
	public User verifyAccount(String email, String otp) throws Exception {
		User user = repo.findById(email).orElseThrow(()-> new InvalidIdException("Email not found..!!"+email));
		if(user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedtime(), LocalDateTime.now()).getSeconds()<(1*60)) {
			user.setVerified(true);
			return repo.save(user);
		}
		else {
			 throw new Exception("Inavlid Otp...!!");
		}
	}

	@Override
	public String regenerateOtp(String email) throws MessagingException, InvalidIdException {
		User user = repo.findById(email).orElseThrow(()-> new InvalidIdException("Email not found..!!"+email));
		String otp=otpUtil.generateOtp();
		emailUtil.sendOtpMail(email, otp);
		user.setOtp(otp);
		user.setOtpGeneratedtime(LocalDateTime.now());
		repo.save(user);
		return "Otp sent....please verify within 1 minute";
	}

	@Override
	public User sendOTP(String email) throws InvalidIdException, MessagingException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyOTP(String email, String otp) throws InvalidIdException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User resetPassword(String email, String password) throws InvalidIdException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = repo.findByEmail(email);
		return user;
	}
}
