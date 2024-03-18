package com.example.JobSupportBackend.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.JobSupportBackend.EmailUtil.EmailUtil;
import com.example.JobSupportBackend.EmailUtil.OtpUtil;
import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.Certification;
import com.example.JobSupportBackend.entity.DeletedAccounts;
import com.example.JobSupportBackend.entity.Education;
import com.example.JobSupportBackend.entity.Experience;
import com.example.JobSupportBackend.entity.Language;
import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.InvalidPasswordException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;
import com.example.JobSupportBackend.repo.CertificationRepository;
import com.example.JobSupportBackend.repo.DeletedAccountsRepository;
import com.example.JobSupportBackend.repo.EducationRepository;
import com.example.JobSupportBackend.repo.ExperienceRepository;
import com.example.JobSupportBackend.repo.LanguageRepository;
import com.example.JobSupportBackend.repo.SkillsRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

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

	@Autowired
	private SkillsRepository skillsRepository;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private CertificationRepository certificationRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private DeletedAccountsRepository accountsRepository;

	@SuppressWarnings("unused")
	private static final int MAX_IMAGE_SIZE = 1024 * 1024; // Example: 1 MB

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public User register(Register register) throws InvalidIdException, MessagingException {
		Optional<User> user2 = repo.findById(register.getEmail());
		if (user2.isPresent()) {
			throw new InvalidIdException("Email already exists...!!!" + register.getEmail());
		} else {
			String otp = otpUtil.generateOtp();
			emailUtil.sendOtpMail(register.getEmail(), otp);
			User user = User.builder().name(register.getUsername()).email(register.getEmail())
					.password(getEncodedPassword(register.getPassword())).otp(otp).otpGeneratedtime(LocalDateTime.now())
					.build();
			return repo.save(user);
		}
	}

	@Override
	public User updateRole(String email, String newRole) throws Exception {
		User uuser = repo.findById(email).orElseThrow(() -> new Exception("Email Id not found..!!!"));
		if (uuser != null) {
			uuser.setRole(newRole);
			return repo.save(uuser);
		} else {
			throw new InvalidIdException("Email not found..!!!" + email);
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
	@Transactional
	public void updateUserImagePathAndStoreInDatabase(String email, MultipartFile file) throws IOException {

		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		// Generate a unique filename
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		// Save the image file to a local directory
		String uploadDir = "C:\\Users\\PURNA\\OneDrive\\Desktop\\saving photos";
		Path directoryPath = Paths.get(uploadDir);
		Files.createDirectories(directoryPath);

		String filePath = Paths.get(uploadDir, uniqueFileName).toString();
		Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

		// Store the image path in the database
		User user = repo.findByEmail(email);
		if (user != null) {
			user.setImagePath(filePath);
			repo.save(user);
		} else {
			throw new IllegalArgumentException("User with email " + email + " does not exist.");
		}
	}

	@Override
	public byte[] getPhotoBytesByEmail(String email) throws IOException {
		// Fetch the user entity by email
		User user = repo.findByEmail(email);
		if (user == null) {
			throw new IllegalArgumentException("User with email " + email + " does not exist.");
		}

		// Get the image path from the user object
		String imagePath = user.getImagePath();
		if (imagePath == null || imagePath.isEmpty()) {
			throw new IllegalArgumentException("User with email " + email + " does not have a photo.");
		}

		// Read the photo bytes from the file
		Path photoPath = Paths.get(imagePath);
		return Files.readAllBytes(photoPath);
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
		User user = repo.findById(email).orElseThrow(() -> new InvalidIdException("Email Id not found..!!!"));
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
		User user = repo.findById(email).orElseThrow(() -> new InvalidIdException("Email not found..!!" + email));
		if (user.getOtp().equals(otp)
				&& Duration.between(user.getOtpGeneratedtime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
			user.setVerified(true);
			return repo.save(user);
		} else {
			throw new Exception("Inavlid Otp...!!");
		}
	}

	@Override
	public String regenerateOtp(String email) throws MessagingException, InvalidIdException {
		User user = repo.findById(email).orElseThrow(() -> new InvalidIdException("Email not found..!!" + email));
		String otp = otpUtil.generateOtp();
		emailUtil.sendOtpMail(email, otp);
		user.setOtp(otp);
		user.setOtpGeneratedtime(LocalDateTime.now());
		repo.save(user);
		return "Otp sent....please verify within 1 minute";
	}

	@Override
	public User sendOTP(String email) throws InvalidIdException, MessagingException, ResourceNotFoundException {
		User user = repo.findById(email).orElseThrow(() -> new InvalidIdException("Email not found..!!" + email));
		if (user.isVerified()) {
			String otp = otpUtil.generateOtp();
			emailUtil.setPassword(email, otp);
			user.setOtp(otp);
			user.setOtpGeneratedtime(LocalDateTime.now());
			return repo.save(user);
		} else {
			throw new ResourceNotFoundException("User email is not Verified..!!!");
		}
	}

	@Override
	public boolean verifyOTP(String email, String otp) throws InvalidIdException {
		User user = repo.findById(email)
				.orElseThrow(() -> new InvalidIdException("User Email Doesnot exists with " + email));
		if (user.getOtp().equals(otp)
				&& Duration.between(user.getOtpGeneratedtime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User resetPassword(String email, String password) throws InvalidIdException {
		User user = repo.findById(email)
				.orElseThrow(() -> new InvalidIdException("User Email Doesnot exists with " + email));
		user.setPassword(getEncodedPassword(password));
		return repo.save(user);
	}

	@Override
	public User getUserByEmail(String email) {
		User user = repo.findByEmail(email);
		return user;
	}

	@Transactional
	@Override
	public User updateFreelancerDetails(String email, User user) throws InvalidIdException {
		User user1 = repo.findById(email)
				.orElseThrow(() -> new InvalidIdException("User Email Doesnot exists with " + email));
		user1.setFirstname(user.getFirstname());
		user1.setLastname(user.getLastname());
		user1.setPhonenumber(user.getPhonenumber());
		user1.setDob(user.getDob());
		user1.setJobtitle(user.getJobtitle());
		user1.setTypeofjob(user.getTypeofjob());
		user1.setDescription(user.getDescription());

		// Delete existing skills associated with the user
		skillsRepository.deleteByUserEmail(email);
		educationRepository.deleteByUserEmail(email);
		certificationRepository.deleteByUserEmail(email);
		experienceRepository.deleteByUserEmail(email);
		languageRepository.deleteByUserEmail(email);

		// Add new skills
		if (user.getSkills() != null) {
			for (Skills skill : user.getSkills()) {
				skill.setUser(user1); // Set the user for the skill
				skillsRepository.save(skill);
			}
		}

		if (user.getEducation() != null) {
			for (Education education : user.getEducation()) {
				education.setUser(user1);
				educationRepository.save(education);
			}
		}

		if (user.getCertification() != null) {
			for (Certification certification : user.getCertification()) {
				certification.setUser(user1);
				certificationRepository.save(certification);
			}
		}

		if (user.getExperience() != null) {
			for (Experience experience : user.getExperience()) {
				experience.setUser(user1);
				experienceRepository.save(experience);
			}
		}

		if (user.getLanguage() != null) {
			for (Language language : user.getLanguage()) {
				language.setUser(user1);
				languageRepository.save(language);
			}
		}

		user1.setFacebook(user.getFacebook());
		user1.setInstagram(user.getInstagram());
		user1.setLinkedin(user.getLinkedin());
		user1.setPersnolurl(user.getPersnolurl());
		user1.setAddress(user.getAddress());
		user1.setCity(user.getCity());
		user1.setState(user.getState());
		user1.setPostcode(user.getPostcode());
		repo.save(user1);
		return user1;
	}

	@Override
	public void changePassword(String email, String password, String newPassword) {
		User user = repo.findByEmail(email);

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new InvalidPasswordException("Old password is incorrect");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		repo.save(user);
	}

	@Override
	public void postReason(String email, DeletedAccounts deletedAccounts) throws InvalidIdException {
		User user = repo.findById(email).orElseThrow(() -> new InvalidIdException("Email not found: " + email));
		if (passwordEncoder.matches(deletedAccounts.getPassword(), user.getPassword())) {
			deletedAccounts.setEmail(email);
			String encodedPassword = getEncodedPassword(deletedAccounts.getPassword());
			deletedAccounts.setPassword(encodedPassword);
			accountsRepository.save(deletedAccounts);
		} else {
			throw new InvalidPasswordException("Incorrect password for the given email: " + email);
		}
	}

}
