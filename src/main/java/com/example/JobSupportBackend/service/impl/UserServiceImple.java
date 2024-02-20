package com.example.JobSupportBackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.dto.SkillsAndExperience;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.UserService;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public User register(Register register) {

		User user = User.builder().username(register.getUsername()).email(register.getEmail())
				.password(getEncodedPassword(register.getPassword())).build();

		return repo.save(user);
	}

	@Override
	public User updateRole(User user, String email) throws Exception {
		User uuser = repo.findById(email).orElseThrow(() -> new Exception("Email Id not found..!!!"));
		uuser.setRole(user.getRole());

		return repo.save(uuser);
	}

	@Override
	public User personalInfo(PersonalInfo personalInfo, String email) throws Exception {
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

	private   List<SkillsAndExperience>  skills;
	
	@Override
	public User skillsandexperience( String email) throws InvalidIdException {
		User user = repo.findById(email).orElseThrow(() -> new InvalidIdException("Email Id not found..!!!"));
		  
		
		return null;
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
		user.setPostcodetype(otherInfo.getPostcodetype());
		user.setDocumenttype(otherInfo.getDocumenttype());
		user.setDocumentnumber(otherInfo.getDocumentnumber());
		return repo.save(user);
	}

}
