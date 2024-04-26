package com.example.JobSupportBackend.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.dto.UserDataDTO;
import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.entity.DeletedAccounts;
import com.example.JobSupportBackend.entity.Portfolio;
import com.example.JobSupportBackend.entity.SendProposal;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.InvalidPasswordException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.CertificationService;
import com.example.JobSupportBackend.service.EducationService;
import com.example.JobSupportBackend.service.ExperienceService;
import com.example.JobSupportBackend.service.LanguageService;
import com.example.JobSupportBackend.service.SkillsService;
import com.example.JobSupportBackend.service.UserService;

import jakarta.mail.MessagingException;

@RestController
public class UserController {

//	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private SkillsService skillsService;

	@Autowired
	private EducationService educationService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private ExperienceService experienceService;

	@Autowired
	private CertificationService certificationService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody Register user) throws InvalidIdException, MessagingException, UnsupportedEncodingException {
		return new ResponseEntity<User>(userService.register(user), HttpStatus.CREATED);
	}

	@PutMapping("/verify/{email}/{otp}")
	public ResponseEntity<User> verifyAccount(@PathVariable String email, @PathVariable String otp) throws Exception {
		return new ResponseEntity<User>(userService.verifyAccount(email, otp), HttpStatus.OK);
	}

	@PutMapping("/regenerate-otp/{email}")
	public ResponseEntity<String> regenerateOtp(@PathVariable String email)
			throws MessagingException, InvalidIdException, UnsupportedEncodingException {
		return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
	}

	@PutMapping("/update/{email}")
	public ResponseEntity<User> role(@PathVariable String email, @RequestParam String newRole) throws Exception {
		User user = userService.updateRole(email, newRole);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/persnolInfo/{email}")
	public ResponseEntity<User> personalInfo(@PathVariable String email, @RequestBody PersonalInfo personalInfo)
			throws Exception {
		return new ResponseEntity<User>(userService.updatePersonalInfo(personalInfo, email), HttpStatus.ACCEPTED);
	}

//	@PostMapping("/upload/{email}")
//	public ResponseEntity<String> uploadPhoto(@PathVariable String email, @RequestParam("file") MultipartFile file) {
//		try {
//			userService.updateUserImagePathAndStoreInDatabase(email, file);
//			return ResponseEntity.ok("Photo uploaded successfully for user with email: " + email);
//		} catch (IOException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Error uploading photo: " + e.getMessage());
//		}
//	}

	@PostMapping("/upload/{email}")
	public ResponseEntity<?> uploadImage(@PathVariable String email, @RequestParam("file") MultipartFile file) {
		try {
			userService.updateUserImagePathAndStoreInDatabase(email, file);
			return ResponseEntity.ok("Image uploaded successfully");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Failed to upload image");
		}
	}

//	@GetMapping("/photo/{email}")
//	public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable String email) {
//		try {
//			// Get the photo bytes for the given email
//			byte[] photoBytes = userService.getPhotoBytesByEmail(email);
//
//			// Create a ByteArrayResource from the photo bytes
//			ByteArrayResource resource = new ByteArrayResource(photoBytes);
//
//			// Return ResponseEntity with the resource
//			return ResponseEntity.ok().header("Content-Type", "image/jpeg").body(resource);
//		} catch (IOException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		} catch (IllegalArgumentException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}

	@GetMapping("/photo/{email}")
	public ResponseEntity<byte[]> getUserPhotoByEmail(@PathVariable String email) {
		try {
			byte[] photoBytes = userService.getPhotoBytesByEmail(email);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photoBytes);
		} catch (IOException e) {
			e.printStackTrace(); // Log the error for debugging purposes
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/updateInfoForEmployeerDashBoard/{email}")
	public ResponseEntity<User> updateInfoForEmployeerDashBoard(@PathVariable String email,
			@RequestBody User updatedUser) {
		try {
			User updatedUserInfo = userService.updateInfoForEmployeerDashBoard(email, updatedUser);
			return new ResponseEntity<>(updatedUserInfo, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@PutMapping("/photoUpdate/{email}")
//	public ResponseEntity<?> updatePhoto(@PathVariable String email, @RequestParam("photo") MultipartFile photo) {
//		try {
//			if (photo.isEmpty()) {
//				return ResponseEntity.badRequest().build(); // Return 400 Bad Request if photo is empty
//			}
//			userService.updatePhotoByEmail(email, photo);
//			return ResponseEntity.ok().build(); // Return 200 OK if photo is updated successfully
//		} catch (IOException e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}

	@PutMapping("/photoUpdate/{email}")
	public ResponseEntity<?> updatePhoto(@PathVariable String email, @RequestParam("photo") MultipartFile photo) {
		try {
			userService.updatePhotoByEmail(email, photo);
			return ResponseEntity.ok().build(); // Return 200 OK if photo is updated successfully
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build(); // Return 400 Bad Request if photo is empty or user does not
														// exist
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 Internal Server Error
																					// for other IO exceptions
		}
	}

	@PutMapping("/otherInfo/{email}")
	public ResponseEntity<User> otherInfo(@PathVariable String email, @RequestBody Otherinfo otherinfo)
			throws Exception {
		return new ResponseEntity<User>(userService.otherinfo(otherinfo, email), HttpStatus.ACCEPTED);
	}

	@PostMapping("/addUserData/{email}")
	public ResponseEntity<User> addUserData(@PathVariable String email, @RequestBody UserDataDTO dataDTO) {
		try {
			if (dataDTO.getSkills() != null) {
				skillsService.addSkills(email, dataDTO.getSkills());
			}
			if (dataDTO.getEducations() != null) {
				educationService.addEducations(email, dataDTO.getEducations());
			}
			if (dataDTO.getCertifications() != null) {
				certificationService.addCertications(email, dataDTO.getCertifications());
			}
			if (dataDTO.getExperiences() != null) {
				experienceService.addExperience(email, dataDTO.getExperiences());
			}
			if (dataDTO.getLanguages() != null) {
				languageService.addLanguages(email, dataDTO.getLanguages());
			}

			User user = userService.getUserByEmail(email);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return an empty body or any
																						// necessary data
		}
	}

	@GetMapping("/getUser/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		User user = userService.getUserByEmail(email);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/employerInfo/{email}")
	public ResponseEntity<User> employerInfo(@PathVariable String email, @RequestBody EmployerInfo employerInfo)
			throws Exception {
		return new ResponseEntity<User>(userService.employerInfo(employerInfo, email), HttpStatus.ACCEPTED);
	}

	@PutMapping("/resetPassword/{email}/{password}")
	public ResponseEntity<User> resetPassword(@PathVariable String email, @PathVariable String password)
			throws Exception {
		if (email != null && password != null) {
			return new ResponseEntity<User>(userService.resetPassword(email, password), HttpStatus.OK);
		} else {
			throw new Exception("Credentials cant be null");

		}
	}

	@GetMapping("/check-email")
	public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
		boolean emailExists = userRepository.existsByEmail(email);
		return ResponseEntity.ok(emailExists);
	}

	@PutMapping("/sendOTP/{email}")
	public ResponseEntity<String> sendOTP(@PathVariable String email) throws Exception {
		if (email == null) {
			throw new Exception("Email cant be null");
		} else {
			return new ResponseEntity<String>(userService.sendOTP(email), HttpStatus.OK);
		}
	}

	@PutMapping("/verifyOTP/{email}/{otp}")
	public ResponseEntity<Boolean> verifyOTP(@PathVariable String otp, @PathVariable String email) throws Exception {
		if (otp == null && email == null) {
			throw new Exception("Email and Otp cant be null");
		} else {
			return new ResponseEntity<Boolean>(userService.verifyOTP(email, otp), HttpStatus.OK);
		}
	}

	@PutMapping("/updateFreelancer/{email}")
	public ResponseEntity<User> updateUserByEmail(@PathVariable String email, @RequestBody User updatedUserData) {
		try {
			User updatedUser = userService.updateFreelancerDetails(email, updatedUserData);
			return ResponseEntity.ok(updatedUser);
		} catch (InvalidIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/deleteSkill/{skillName}")
	public void deleteSkill(@PathVariable String skillName) {
		skillsService.findByName(skillName);
	}

	@PutMapping("/change-password/{email}/{password}/{newPassword}")
	public ResponseEntity<?> changePassword(@PathVariable String email, @PathVariable String password,
			@PathVariable String newPassword) {
		try {
			userService.changePassword(email, password, newPassword);
			return ResponseEntity.ok().build();
		} catch (InvalidPasswordException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/postReason/{email}")
	public ResponseEntity<String> postReason(@PathVariable String email, @RequestBody DeletedAccounts accounts) {
		try {
			userService.postReason(email, accounts);
			return ResponseEntity.ok("Reason posted successfully for email: " + email);
		} catch (InvalidIdException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (InvalidPasswordException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PostMapping("/postPortfolio/{email}")
	public ResponseEntity<Portfolio> postPortfolio(@PathVariable String email, @RequestParam("title") String title,
			@RequestParam("link") String link, @RequestParam("photo") MultipartFile photo) {
		try {
			// Create a new Portfolio object
			Portfolio portfolio = new Portfolio();
			portfolio.setTitle(title);
			portfolio.setLink(link);

			// Call service method to handle the portfolio
			Portfolio addedPortfolio = userService.addPortfolio(email, portfolio, photo);

			// Return the response entity
			return ResponseEntity.status(HttpStatus.CREATED).body(addedPortfolio);
		} catch (ResourceNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload the portfolio image",
					e);
		} catch (Exception e) {
			// Handle generic exceptions
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"An error occurred while adding the portfolio", e);
		}
	}

	@GetMapping("/getPortfolios/{email}")
	public ResponseEntity<List<Portfolio>> getAllPortfoliosByUser(@PathVariable String email) {
		try {
			List<Portfolio> portfolios = userService.getAllPortfoliosWithImages(email);
			return new ResponseEntity<>(portfolios, HttpStatus.OK);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/updatePortfolio/{email}/{title1}")
	public ResponseEntity<Portfolio> updatePortfolio(@PathVariable String email, @PathVariable String title1,
			@RequestParam("title") String title, @RequestParam("link") String link,
			@RequestParam("photo") MultipartFile photo)
			throws InvalidIdException, IOException, ResourceNotFoundException {
		Portfolio portfolio = new Portfolio();
		portfolio.setTitle(title);
		portfolio.setLink(link);

		Portfolio updatedPortfolio = userService.updatePortfolio(email, title1, portfolio, photo);
		return ResponseEntity.ok(updatedPortfolio);
	}

	@DeleteMapping("/deletePortfolio/{email}/{title}")
	public ResponseEntity<String> deletePortfolio(@PathVariable String email, @PathVariable String title)
			throws ResourceNotFoundException {
		String deletePortfolio = userService.deletePortfolio(email, title);
		return ResponseEntity.accepted().body(deletePortfolio);
	}

	@GetMapping("/getPortByEmail&Title/{email}/{title}")
	public ResponseEntity<Portfolio> getPortfolioByTitleAndEmail(@PathVariable String email,
			@PathVariable String title) {
		Portfolio portfolioByEmailAndTitle = userService.getPortfolioByEmailAndTitle(email, title);
		return ResponseEntity.ok(portfolioByEmailAndTitle);
	}

	@GetMapping("/getAllUsers/{role}")
	public ResponseEntity<List<User>> getAllUsers(@PathVariable String role) {
		List<User> allUsers = userService.getAllUsers(role);
		return ResponseEntity.ok(allUsers);
	}

	@GetMapping("/getAllUsersByStatus/{role}/{status}")
	public ResponseEntity<List<User>> getUserByStatus(@PathVariable String role, @PathVariable String status) {
		List<User> allUsersByStatus = userService.getAllUsersByStatus(role, status);
		return ResponseEntity.ok(allUsersByStatus);
	}

	@GetMapping("/totalUsersByRole/{role}")
	public ResponseEntity<Integer> getTotalFreelancersCount(@PathVariable String role) {
		int totalCount = userService.getTotalUsersByRole(role);
		return ResponseEntity.ok(totalCount);
	}

	@GetMapping("/active/{role}")
	public ResponseEntity<Integer> getActiveFreelancersCount(@PathVariable String role) {
		int activeCount = userService.getActiveUsersCount(role);
		return ResponseEntity.ok(activeCount);
	}

	@GetMapping("/deactivated/{role}")
	public ResponseEntity<Integer> getDeactivatedFreelancersCount(@PathVariable String role) {
		int deactivatedCount = userService.getDeactivatedUsersCount(role);
		return ResponseEntity.ok(deactivatedCount);
	}

	@GetMapping("/accountStatus/{email}")
	public ResponseEntity<String> getUserAccountStatus(@PathVariable String email) throws InvalidIdException {
		String status = userService.getUserAccountStatus(email);
		return ResponseEntity.ok().body(status);
	}

	@PostMapping("/sendProposal/{adminProjectId}/{email}")
	public ResponseEntity<SendProposal> postProposal(@PathVariable long adminProjectId, @PathVariable String email,
			@RequestBody SendProposal proposal) {
		SendProposal savedProposal = userService.sendProposal(adminProjectId, email, proposal);
		return new ResponseEntity<>(savedProposal, HttpStatus.CREATED);
	}

	@GetMapping("/getProposals/{email}")
	public ResponseEntity<List<SendProposal>> getProposals(@PathVariable String email) {
		List<SendProposal> proposals = userService.getProposals(email);
		return new ResponseEntity<List<SendProposal>>(proposals, HttpStatus.OK);
	}

	@GetMapping("/getProposalById/{proposalId}")
	public ResponseEntity<SendProposal> getProposalById(@PathVariable int proposalId) throws ResourceNotFoundException {
		SendProposal proposalById = userService.getProposalById(proposalId);
		return new ResponseEntity<SendProposal>(proposalById, HttpStatus.OK);
	}

	@PutMapping("/updateProposal/{proposalId}")
	public ResponseEntity<SendProposal> updateProposalById(@PathVariable int proposalId,
			@RequestBody SendProposal proposal) throws ResourceNotFoundException {
		SendProposal updateProposals = userService.updateProposals(proposalId, proposal);
		return new ResponseEntity<SendProposal>(updateProposals, HttpStatus.OK);
	}

	@DeleteMapping("/deleteProposal/{proposalId}")
	public ResponseEntity<String> deleteProposal(@PathVariable int proposalId) {
		String deleteProposal = userService.deleteProposal(proposalId);
		return new ResponseEntity<String>(deleteProposal, HttpStatus.OK);
	}

	@GetMapping("/getProposalsByProjectId/{projectId}")
	public ResponseEntity<List<SendProposal>> getProposalsByProjectId(@PathVariable Long projectId) {
		List<SendProposal> proposalsByProjectId = userService.getProposalsByProjectId(projectId);
		return new ResponseEntity<List<SendProposal>>(proposalsByProjectId, HttpStatus.OK);
	}
	
	 @GetMapping("/onGoingProjects")
	    public ResponseEntity<List<AdminPostProject>> getOngoingProjects(@RequestParam String email) {
	        try {
	            List<AdminPostProject> ongoingProjects = userService.onGoingProjects(email);
	            return ResponseEntity.ok(ongoingProjects);
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().body(null);
	        }
	    }
}