package com.example.JobSupportBackend.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.example.JobSupportBackend.EmailUtil.EmailUtil;
import com.example.JobSupportBackend.EmailUtil.OtpUtil;
import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.entity.Certification;
import com.example.JobSupportBackend.entity.DeletedAccounts;
import com.example.JobSupportBackend.entity.Education;
import com.example.JobSupportBackend.entity.Experience;
import com.example.JobSupportBackend.entity.Language;
import com.example.JobSupportBackend.entity.Milestone;
import com.example.JobSupportBackend.entity.Portfolio;
import com.example.JobSupportBackend.entity.SendProposal;
import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.InvalidPasswordException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;
import com.example.JobSupportBackend.repo.AdminPostProjectRpository;
import com.example.JobSupportBackend.repo.CertificationRepository;
import com.example.JobSupportBackend.repo.DeletedAccountsRepository;
import com.example.JobSupportBackend.repo.EducationRepository;
import com.example.JobSupportBackend.repo.ExperienceRepository;
import com.example.JobSupportBackend.repo.LanguageRepository;
import com.example.JobSupportBackend.repo.MilestoneRepository;
import com.example.JobSupportBackend.repo.PortfolioRepository;
import com.example.JobSupportBackend.repo.ProposalsRepository;
import com.example.JobSupportBackend.repo.SkillsRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
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

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private ProposalsRepository proposalsRepository;

	@Autowired
	private AdminPostProjectRpository adminPostProjectRepository;

	@Autowired
	private MilestoneRepository milestoneRepository;

	@Value("${aws.bucketName}")
	private String bucketName;

	@Value("${aws.s3.profilePhotosFolder}")
	private String folderName;

	@Value("${aws.s3.portfoliosFolder}")
	private String portfoliosFolder;

	@Value("${aws.region}")
	private String awsRegion;

	@SuppressWarnings("unused")
	private static final int MAX_IMAGE_SIZE = 1024 * 1024; // Example: 1 MB

	String uploadDir = "C:\\Users\\srich\\OneDrive\\Desktop\\PortfolioImages";

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
			User user = User.builder().name(register.getName()).email(register.getEmail())
					.password(getEncodedPassword(register.getPassword())).otp(otp).otpGeneratedtime(LocalDateTime.now())
					.build();
			user.setStatus("Yet To Be");
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

		// Generate a unique filename
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		// Specify the AWS region explicitly
		Regions region = Regions.fromName(awsRegion);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build();

		// Upload the image file to AWS S3
		try (InputStream inputStream = file.getInputStream()) {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			String s3Key = folderName + "/" + uniqueFileName;
			s3Client.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
		}

		// Store the image path in the database
		String imagePath = "https://" + bucketName + ".s3.amazonaws.com/" + folderName + "/" + uniqueFileName;
		User user = repo.findByEmail(email);
		if (user != null) {
			user.setImagePath(imagePath);
			repo.save(user);
		} else {
			throw new IllegalArgumentException("User with email " + email + " does not exist.");
		}
	}

//	@Override
//	@Transactional
//	public void updateUserImagePathAndStoreInDatabase(String email, MultipartFile file) throws IOException {
//
//		// Generate a unique filename
//		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//
//		if (file.isEmpty()) {
//			throw new IllegalArgumentException("File is empty");
//		}
//
//		// Save the image file to a local directory
//		String uploadDir = "C:\\Users\\srich\\OneDrive\\Desktop\\Profile Pics";
//		Path directoryPath = Paths.get(uploadDir);
//		Files.createDirectories(directoryPath);
//
//		String filePath = Paths.get(uploadDir, uniqueFileName).toString();
//		Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
//
//		// Store the image path in the database
//		User user = repo.findByEmail(email);
//		if (user != null) {
//			user.setImagePath(filePath);
//			repo.save(user);
//		} else {
//			throw new IllegalArgumentException("User with email " + email + " does not exist.");
//		}
//	}

//	@Override
//	public byte[] getPhotoBytesByEmail(String email) throws IOException {
//		// Fetch the user entity by email
//		User user = repo.findByEmail(email);
//		if (user == null) {
//			throw new IllegalArgumentException("User with email " + email + " does not exist.");
//		}
//
//		// Get the image path from the user object
//		String imagePath = user.getImagePath();
//		if (imagePath == null || imagePath.isEmpty()) {
//			throw new IllegalArgumentException("User with email " + email + " does not have a photo.");
//		}
//
//		// Read the photo bytes from the file
//		Path photoPath = Paths.get(imagePath);
//		return Files.readAllBytes(photoPath);
//	}

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

		// Extract the S3 key from the image path
		String s3Key = imagePath.substring(imagePath.lastIndexOf("/") + 1);

		// Specify the AWS region explicitly
		Regions region = Regions.fromName(awsRegion);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build();

		// Download the image bytes from AWS S3
		S3Object object = s3Client.getObject(bucketName, folderName + "/" + s3Key);
		try (InputStream inputStream = object.getObjectContent()) {
			return IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			throw new IOException("Error reading image from S3", e);
		}
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

//	@Override
//	public Portfolio addPortfolio(String email, Portfolio portfolio, MultipartFile multipartFile)
//			throws ResourceNotFoundException, IOException {
//
//		String photoPath = addPortfolioImage(multipartFile);
//		User user = repo.findByEmail(email);
//		if (user.isVerified()) {
//			portfolio.setUser(user);
//			portfolio.setPhoto_path(photoPath);
//			return portfolioRepository.save(portfolio);
//		} else {
//			throw new ResourceNotFoundException("Account is not Verified...!!!");
//		}
//	}
//
//	private String addPortfolioImage(MultipartFile multipartFile) throws IOException {
//		if (multipartFile.isEmpty()) {
//			throw new IllegalArgumentException("File is empty");
//		}
//
//		String uniqueFileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
//
//		Path directoryPath = Paths.get(uploadDir);
//		Files.createDirectories(directoryPath);
//
//		String filePath = Paths.get(uploadDir, uniqueFileName).toString();
//		try {
//			Files.copy(multipartFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			// Handle file upload failure
//			System.err.println("Error uploading portfolio image: " + e.getMessage());
//			throw e; // Re-throw the exception to propagate the error to the caller
//		}
//
//		return uniqueFileName; // Return the relative file path
//	}

	@Override
	public Portfolio addPortfolio(String email, Portfolio portfolio, MultipartFile multipartFile)
			throws ResourceNotFoundException, IOException {

		// Generate a unique filename
		String uniqueFileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();

		if (multipartFile.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		// Specify the AWS region explicitly
		Regions region = Regions.fromName(awsRegion);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build();

		// Upload the image file to AWS S3
		try (InputStream inputStream = multipartFile.getInputStream()) {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(multipartFile.getSize());
			String s3Key = portfoliosFolder + "/" + uniqueFileName;
			s3Client.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
		}

		// Store the image path in the database
		String imagePath = "https://" + bucketName + ".s3.amazonaws.com/" + portfoliosFolder + "/" + uniqueFileName;

		// Retrieve the user associated with the email
		User user = repo.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("User with email " + email + " not found.");
		}

		// Check if the user is verified
		if (!user.isVerified()) {
			throw new ResourceNotFoundException("Account is not verified.");
		}

		// Set the user and photo path for the portfolio
		portfolio.setUser(user);
		portfolio.setPhoto_path(imagePath);

		// Save the portfolio to the database
		return portfolioRepository.save(portfolio);
	}

		
	
	
	@Override
	public List<Portfolio> getAllPortfoliosWithImages(String email) throws IOException {
		// Retrieve all portfolios for the given user email
		List<Portfolio> portfolios = portfolioRepository.findByUserEmail(email);

		// Iterate through each portfolio and populate the photo_path with the image URL
		for (Portfolio portfolio : portfolios) {
			// Construct the image URL based on the photo_path stored in the database
			byte[] photoPath = constructImageUrl(portfolio.getPhoto_path());
			portfolio.setImageBytes(photoPath);
		}

		// Return the list of portfolios with image URLs
		return portfolios;
	}

	private byte[] constructImageUrl(String imagePath) throws IOException {
		String s3Key = imagePath.substring(imagePath.lastIndexOf("/") + 1);

		// Specify the AWS region explicitly
		Regions region = Regions.fromName(awsRegion);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build();

		// Download the image bytes from AWS S3
		S3Object object = s3Client.getObject(bucketName, portfoliosFolder + "/" + s3Key);
		try (InputStream inputStream = object.getObjectContent()) {
			return IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			throw new IOException("Error reading image from S3", e);
		}
	}

//	 public List<Portfolio> getAllPortfoliosWithImages(String email) {
//	        List<Portfolio> portfolios = portfolioRepository.findByUserEmail(email);
//	        for (Portfolio portfolio : portfolios) {
//	            loadPortfolioImage(portfolio);
//	        }
//	        return portfolios;
//	    }
//
//	    private void loadPortfolioImage(Portfolio portfolio) {
//	        String photoPath = portfolio.getPhoto_path();
//	        if (photoPath != null && !photoPath.isEmpty()) {
//	            try {
//	                S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, portfoliosFolder + "/" + photoPath));
//	                InputStream inputStream = s3Object.getObjectContent();
//	                byte[] bytes = IOUtils.toByteArray(inputStream);
//	                portfolio.setImageBytes(bytes);
//	                inputStream.close();
//	            } catch (AmazonServiceException | IOException e) {
//	                System.err.println("Error loading portfolio image: " + e.getMessage());
//	                // Handle error loading image
//	            }
//	        }
//	    }

//	@Override
//	public List<Portfolio> getAllPortfoliosWithImages(String email) throws IOException {
//		List<Portfolio> portfolios = portfolioRepository.findByUserEmail(email);
//		for (Portfolio portfolio : portfolios) {
//			loadPortfolioImage(portfolio);
//		}
//		return portfolios;
//	}

//	private ByteArrayResource loadPortfolioImage(Portfolio portfolio) {
//	    if (portfolio.getPhoto_path() != null && !portfolio.getPhoto_path().isEmpty()) {
//	        String filePath = Paths.get(uploadDir, portfolio.getPhoto_path()).toString();
//	        try {
//	            byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
//	            portfolio.setImageBytes(imageBytes);
//	            ByteArrayResource resource = new ByteArrayResource(imageBytes);
//	            return resource;
//	        } catch (IOException e) {
//	            // Handle file not found or other IO errors
//	            System.err.println("Error loading image for portfolio: " + portfolio.getPortfolio_Id());
//	            e.printStackTrace();
//	            portfolio.setImageBytes(null); // Setting imageBytes to null or any default value
//	            return null;
//	        }
//	    }
//	}

//	private ByteArrayResource loadPortfolioImage(Portfolio portfolio) {
//		if (portfolio.getPhoto_path() != null && !portfolio.getPhoto_path().isEmpty()) {
//			String filePath = Paths.get(uploadDir, portfolio.getPhoto_path()).toString();
//			try {
//				byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
//				return new ByteArrayResource(imageBytes);
//			} catch (IOException e) {
//				// Handle file not found or other IO errors
//				System.err.println("Error loading image for portfolio: " + portfolio.getPortfolio_Id());
//				e.printStackTrace();
//				return null;
//			}
//		}
//		return null;
//	}

	@Override
	public Portfolio updatePortfolio(String email, String title1, Portfolio portfolio, MultipartFile photo)
			throws InvalidIdException, IOException, ResourceNotFoundException {

		// Generate a unique filename
		String uniqueFileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();

		if (photo.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		// Specify the AWS region explicitly
		Regions region = Regions.fromName(awsRegion);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build();

		// Upload the image file to AWS S3
		try (InputStream inputStream = photo.getInputStream()) {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(photo.getSize());
			String s3Key = portfoliosFolder + "/" + uniqueFileName;
			s3Client.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
		}

		// Store the image path in the database
		String imagePath = "https://" + bucketName + ".s3.amazonaws.com/" + portfoliosFolder + "/" + uniqueFileName;

		List<Portfolio> portfolios = portfolioRepository.findByUserEmail(email);

		for (Portfolio port : portfolios) {
			if (port.getTitle().equals(title1)) {
				port.setTitle(portfolio.getTitle());
				port.setLink(portfolio.getLink());
				port.setPhoto_path(imagePath);
				return portfolioRepository.save(port);
			}
		}
		// Save the portfolio to the database
		return portfolioRepository.save(portfolio);
	}

	@Override
	public String deletePortfolio(String email, String title) throws ResourceNotFoundException {
		List<Portfolio> portfolios = portfolioRepository.findByUserEmail(email);
		for (Portfolio portfolio : portfolios) {
			if (portfolio.getTitle().equals(title)) {
				portfolioRepository.delete(portfolio);
				return "Portfolio is deleted";
			}
		}
		throw new ResourceNotFoundException("Title not found: " + title);
	}

	@Override
	public Portfolio getPortfolioByEmailAndTitle(String email, String title) {
		Portfolio portfolio = portfolioRepository.findByUserEmailAndTitle(email, title);
		return portfolio;
	}

	@Override
	public List<User> getAllUsers(String role) {
		return repo.findByRole(role);
	}

	@Override
	public List<User> getAllUsersByStatus(String role, String status) {
		return repo.findByRoleAndStatus(role, status);
	}

	@Override
	public int getTotalUsersByRole(String role) {
		return repo.countByRole(role);
	}

	@Override
	public int getActiveUsersCount(String role) {
		return repo.countByRoleAndStatus(role, "Active");
	}

	@Override
	public int getDeactivatedUsersCount(String role) {
		return repo.countByRoleAndStatus(role, "De-Activated");
	}

	@Override
	public String getUserAccountStatus(String email) throws InvalidIdException {
		User byEmail = repo.findByEmail(email);
		if (byEmail != null) {
			return byEmail.getStatus();
		} else {
			throw new InvalidIdException("Email not found with " + email);
		}
	}

	@Override
	public SendProposal sendProposal(long adminProjectId, String email, SendProposal proposal) {

		Optional<AdminPostProject> adminProjectOptional = adminPostProjectRepository.findById(adminProjectId);
		Optional<User> userOptional = repo.findById(email);

		// Check if both entities are present
		if (adminProjectOptional.isPresent() && userOptional.isPresent()) {
			SendProposal proposal1 = new SendProposal();
			proposal1.setProposedPrice(proposal.getProposedPrice());
			proposal1.setEstimatedDelivery(proposal.getEstimatedDelivery());
			proposal1.setCoverLetter(proposal.getCoverLetter());
			proposal1.setProposalStatus("Yet TO Be");
			proposal1.setAdminPostProject(adminProjectOptional.get());
			proposal1.setUser(userOptional.get());

			SendProposal savedProposal = proposalsRepository.save(proposal1);

			// Save milestones
			List<Milestone> milestones = new ArrayList<>();
			for (Milestone milestone : proposal.getMilestones()) {
				milestone.setMilestoneName(milestone.getMilestoneName());
				milestone.setPrice(milestone.getPrice());
				milestone.setStartdate(milestone.getStartdate());
				milestone.setEnddate(milestone.getEnddate());
				milestone.setSendProposal(savedProposal); // Associate milestone with the saved proposal
				milestones.add(milestone);
			}
			milestoneRepository.saveAll(milestones);

			// Associate milestones with proposal
			savedProposal.setMilestones(milestones);
			return savedProposal;
		} else {
			throw new EntityNotFoundException("Admin project or user not found");
		}
	}

	@Override
	public List<SendProposal> getProposals(String email) {
		return proposalsRepository.findByUserEmail(email);
	}

	@Override
	public SendProposal getProposalById(int proposalId) throws ResourceNotFoundException {
		Optional<SendProposal> byId = proposalsRepository.findById(proposalId);
		if (byId.isPresent()) {
			return byId.get();
		} else {
			throw new ResourceNotFoundException("Proposal doesnot exists...!!!");
		}
	}

	@Override
	public SendProposal updateProposals(int proposalId, SendProposal sendProposal) throws ResourceNotFoundException {
		SendProposal proposal = proposalsRepository.findById(proposalId)
				.orElseThrow(() -> new ResourceNotFoundException("Proposal not exists..!!"));
		proposal.setCoverLetter(sendProposal.getCoverLetter());
		proposal.setProposedPrice(sendProposal.getProposedPrice());
		proposal.setProposalStatus("yet To Be");
		proposal.setEstimatedDelivery(sendProposal.getEstimatedDelivery());

		milestoneRepository.deleteByProposalId(proposalId);
		if (sendProposal.getMilestones() != null) {
			for (Milestone ml : sendProposal.getMilestones()) {
				ml.setSendProposal(proposal);
				milestoneRepository.save(ml);
			}
		}
		return proposalsRepository.save(proposal);
	}

	@Override
	public String deleteProposal(int proposalId) {
		SendProposal sendProposal = proposalsRepository.findById(proposalId).get();
		milestoneRepository.deleteByProposalId(sendProposal.getProposalId());
		proposalsRepository.delete(sendProposal);
		return "Proposal Deleted..!!!";
	}

	@Override
	public List<SendProposal> getProposalsByProjectId(Long id) {
		List<SendProposal> proposals = proposalsRepository.findByAdminPostProjectId(id);
		return proposals;
	}

	@Override
	public User updateInfoForEmployeerDashBoard(String email, User updatedUser) throws Exception {
		User existingUser = repo.findByEmail(email);

		if (existingUser == null) {
			throw new Exception("User not found");
		}

		// Update only the properties that are provided in the updatedUser object
		if (updatedUser.getFirstname() != null && !updatedUser.getFirstname().isEmpty()) {
			existingUser.setFirstname(updatedUser.getFirstname());
		}
		if (updatedUser.getLastname() != null && !updatedUser.getLastname().isEmpty()) {
			existingUser.setLastname(updatedUser.getLastname());
		}

		if (updatedUser.getPhonenumber() != 0) {
			existingUser.setPhonenumber(updatedUser.getPhonenumber());
		}

		if (updatedUser.getEcompany() != null && !updatedUser.getEcompany().isEmpty()) {
			existingUser.setEcompany(updatedUser.getEcompany());
		}
		if (updatedUser.getEtagline() != null && !updatedUser.getEtagline().isEmpty()) {
			existingUser.setEtagline(updatedUser.getEtagline());
		}
		if (updatedUser.getEstablishdate() != null && !updatedUser.getEstablishdate().isEmpty()) {
			existingUser.setEstablishdate(updatedUser.getEstablishdate());
		}
		if (updatedUser.getEcompanyownername() != null && !updatedUser.getEcompanyownername().isEmpty()) {
			existingUser.setEcompanyownername(updatedUser.getEcompanyownername());
		}
		if (updatedUser.getIndustry() != null && !updatedUser.getIndustry().isEmpty()) {
			existingUser.setIndustry(updatedUser.getIndustry());
		}
		if (updatedUser.getEwebsite() != null && !updatedUser.getEwebsite().isEmpty()) {
			existingUser.setEwebsite(updatedUser.getEwebsite());
		}
		if (updatedUser.getEteamsize() != null && !updatedUser.getEteamsize().isEmpty()) {
			existingUser.setEteamsize(updatedUser.getEteamsize());
		}
		if (updatedUser.getEdescribe() != null && !updatedUser.getEdescribe().isEmpty()) {
			existingUser.setEdescribe(updatedUser.getEdescribe());
		}

		if (updatedUser.getFacebook() != null) {
			existingUser.setFacebook(updatedUser.getFacebook());
		}
		if (updatedUser.getInstagram() != null) {
			existingUser.setInstagram(updatedUser.getInstagram());
		}
		if (updatedUser.getLinkedin() != null) {
			existingUser.setLinkedin(updatedUser.getLinkedin());
		}
		if (updatedUser.getPersnolurl() != null) {
			existingUser.setPersnolurl(updatedUser.getPersnolurl());
		}
		if (updatedUser.getAddress() != null) {
			existingUser.setAddress(updatedUser.getAddress());
		}
		if (updatedUser.getCity() != null) {
			existingUser.setCity(updatedUser.getCity());
		}
		if (updatedUser.getState() != null) {
			existingUser.setState(updatedUser.getState());
		}
		if (updatedUser.getPostcode() != null) {
			existingUser.setPostcode(updatedUser.getPostcode());
		}

		if (updatedUser.getDocumenttype() != null) {
			existingUser.setDocumenttype(updatedUser.getDocumenttype());
		}
		if (updatedUser.getDocumentnumber() != null) {
			existingUser.setDocumentnumber(updatedUser.getDocumentnumber());
		}

		// Similarly update other properties as needed

		return repo.save(existingUser);
	}

//	@Override
//	@Transactional
//	public void updatePhotoByEmail(String email, MultipartFile photo) throws IOException {
//		// Fetch the user from the database
//		User user = repo.findByEmail(email);
//		if (user != null) {
//			// Delete the old photo from the folder
//			deletePhotoFromFileSystem(user.getImagePath());
//
//			// Save the new photo to the folder and update the user's photo path in the
//			// database
//			String imagePath = savePhotoToFileSystem(photo);
//			user.setImagePath(imagePath);
//			repo.save(user);
//		} else {
//			throw new IllegalArgumentException("User with email " + email + " does not exist.");
//		}
//	}
//
//	private void deletePhotoFromFileSystem(String imagePath) throws IOException {
//		if (imagePath != null) {
//			Path path = Paths.get(imagePath);
//			Files.deleteIfExists(path);
//		}
//	}
//
//	private String savePhotoToFileSystem(MultipartFile photo) throws IOException {
//		// Generate a unique filename for the new photo
//		String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
//		// Define the upload directory
//		String uploadDir = "C:\\Users\\srich\\OneDrive\\Desktop\\Profile Pics";
//		// Create the directory if it doesn't exist
//		Path directoryPath = Paths.get(uploadDir);
//		Files.createDirectories(directoryPath);
//		// Save the photo to the upload directory
//		Path filePath = Paths.get(uploadDir, fileName);
//		Files.write(filePath, photo.getBytes());
//		return filePath.toString();
//	}

	@Override
	@Transactional
	public void updatePhotoByEmail(String email, MultipartFile photo) throws IOException {
		// Fetch the user from the database
		User user = repo.findByEmail(email);
		if (user != null) {
			if (photo.isEmpty()) {
				throw new IllegalArgumentException("Photo is empty");
			}

			// Generate a unique filename for the new photo
			String uniqueFileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();

			// Specify the AWS region explicitly
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(awsRegion).build();

			// Upload the photo file to AWS S3
			try (InputStream inputStream = photo.getInputStream()) {
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentLength(photo.getSize());
				String s3Key = folderName + "/" + uniqueFileName;
				s3Client.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
				String imagePath = "https://" + bucketName + ".s3.amazonaws.com/" + folderName + "/" + uniqueFileName;

				// Update the user's photo path in the database
				user.setImagePath(imagePath);
				repo.save(user);
			}
		} else {
			throw new IllegalArgumentException("User with email " + email + " does not exist.");
		}
	}
}
