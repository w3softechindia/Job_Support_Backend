package com.example.JobSupportBackend.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.JobSupportBackend.dto.ProjectDTO;
import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.entity.PostProject;
import com.example.JobSupportBackend.entity.ProjectFile;
import com.example.JobSupportBackend.repo.AdminPostProjectRpository;
import com.example.JobSupportBackend.repo.ProjectFileRepository;
import com.example.JobSupportBackend.repo.ProjectRepo;
import com.example.JobSupportBackend.service.ProjectService;

import jakarta.mail.internet.ParseException;

@RestController
public class ProjectController {
	@Autowired
	private ProjectService postProjectService;

	@Autowired
	private ProjectFileRepository projectFileRepository;
	@Autowired
	private AdminPostProjectRpository adminPostProjectRpository;

	@Value("${aws.bucketName}")
	private String bucketName;

	@Value("${aws.s3.folderNamee}")
	private String folderNamee;

	@Autowired
	private ProjectRepo repooo;

	@PostMapping("/addproject/{userEmail}")
	public ResponseEntity<PostProject> createProject(@RequestBody PostProject project, @PathVariable String userEmail)
			throws ParseException, java.text.ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String deadlineDateString = sdf.format(project.getDeadline_date());

		Date deadlineDate = sdf.parse(deadlineDateString);

		project.setDeadline_date(deadlineDate);

		if ("hourly".equals(project.getActive_rate())) {
			String hourlyRateFromString = project.getHourly_rate_from();
			String hourlyRateToString = project.getHourly_rate_to();

			if (hourlyRateFromString != null && hourlyRateToString != null && !hourlyRateFromString.isEmpty()
					&& !hourlyRateToString.isEmpty()) {
				try {

					int hourlyRateFrom = Integer.parseInt(hourlyRateFromString);
					int hourlyRateTo = Integer.parseInt(hourlyRateToString);

					String budgetAmount = hourlyRateFrom + " - " + hourlyRateTo;
					project.setBudget_amount(budgetAmount);
				} catch (NumberFormatException e) {

					e.printStackTrace();
				}
			} else {

			}
		}

		PostProject savedProject = postProjectService.saveProject(project, userEmail);

		AdminPostProject adminProject = new AdminPostProject();

		adminProject.setProject_id(savedProject.getId());
		adminProject.setUser(savedProject.getUser());
		adminProject.setProject_title(savedProject.getProject_title());
		adminProject.setProject_category(savedProject.getProject_category());
		adminProject.setProject_duration(savedProject.getProject_duration());
		adminProject.setDeadline_date(savedProject.getDeadline_date());
		adminProject.setFreelancer_type(savedProject.getFreelancer_type());
		adminProject.setFreelancer_level(savedProject.getFreelancer_level());
		adminProject.setActive_rate(savedProject.getActive_rate());
		adminProject.setHourly_rate_from(savedProject.getHourly_rate_from());
		adminProject.setHourly_rate_to(savedProject.getHourly_rate_to());
		adminProject.setFixed_rate(savedProject.getFixed_rate());
		adminProject.setLanguages(savedProject.getLanguages());
		adminProject.setLanguage_fluency(savedProject.getLanguage_fluency());
		adminProject.setBudget_amount(savedProject.getBudget_amount());
		adminProject.setDescription(savedProject.getDescription());
		adminProject.setNumber_of_files(savedProject.getNumber_of_files());

		adminProject.setSkills(new ArrayList<>(savedProject.getSkills()));
		adminProject.setTags(new ArrayList<>(savedProject.getTags()));
		adminPostProjectRpository.save(adminProject);

		return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/projects/{userEmail}")
	public ResponseEntity<List<ProjectDTO>> getProjectsByUserEmail(@PathVariable String userEmail) {
		try {
			List<PostProject> projects = postProjectService.getProjectsByUserEmail(userEmail);
			List<ProjectDTO> projectDTOs = new ArrayList<>();
			for (PostProject project : projects) {
				ProjectDTO dto = new ProjectDTO();
				dto.setUserEmail(userEmail);
				dto.setId(project.getId());
				dto.setProjectTitle(project.getProject_title());
				dto.setProjectCategory(project.getProject_category());
				dto.setProject_duration(project.getProject_duration());
				dto.setDeadline_date(project.getDeadline_date());
				dto.setFreelancer_type(project.getFreelancer_type());
				dto.setFreelancer_level(project.getFreelancer_level());
				dto.setActive_rate(project.getActive_rate());
				dto.setHourly_rate_from(project.getHourly_rate_from());
				dto.setHourly_rate_to(project.getHourly_rate_to());
				dto.setFixed_rate(project.getFixed_rate());
				dto.setLanguages(project.getLanguages());
				dto.setLanguage_fluency(project.getLanguage_fluency());
				dto.setBudget_amount(project.getBudget_amount());
				dto.setDescription(project.getDescription());
				dto.setNumber_of_files(project.getNumber_of_files());
				dto.setSkills(project.getSkills());
				dto.setTags(project.getTags());
				dto.setStatus(project.getStatus());
				projectDTOs.add(dto);
			}
			return ResponseEntity.ok(projectDTOs);
		} catch (Exception ex) {
			// Handle the exception gracefully
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

//	@PostMapping("/files/{projectId}")
//	public ResponseEntity<String> uploadFile(@PathVariable Long projectId, @RequestParam("file") MultipartFile file) {
//		try {
//			// Check if project exists
//			Optional<PostProject> optionalPostProject = postProjectService.findById(projectId);
//			if (!optionalPostProject.isPresent()) {
//				return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
//			}
//
//			// Save file to local folder
//			String filePath = saveFileLocally(file);
//
//			// Save file path to database
//			ProjectFile projectFile = new ProjectFile();
//			projectFile.setFilePath(filePath);
//			projectFile.setPostProject(optionalPostProject.get());
//			projectFileRepository.save(projectFile);
//
//			return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	private String saveFileLocally(MultipartFile file) throws IOException {
//		String folderPath = "C:\\Users\\srich\\OneDrive\\Desktop\\Project Files"; // Set your folder path here
//		String fileName = file.getOriginalFilename();
//		Path filePath = Paths.get(folderPath + File.separator + fileName);
//		Files.write(filePath, file.getBytes());
//		return filePath.toString();
//	}

	@PostMapping("/files/{projectId}")
	public ResponseEntity<String> uploadFile(@PathVariable Long projectId, @RequestParam("file") MultipartFile file) {
		try {
			// Check if project exists
			Optional<PostProject> optionalPostProject = postProjectService.findById(projectId);
			if (!optionalPostProject.isPresent()) {
				return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
			}

			// Save file to AWS S3 bucket
			String filePath = saveFileToS3(file);

			// Save file path to database
			ProjectFile projectFile = new ProjectFile();
			projectFile.setFilePath(filePath);
			projectFile.setPostProject(optionalPostProject.get());
			projectFileRepository.save(projectFile);

			return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String saveFileToS3(MultipartFile file) throws IOException, URISyntaxException {
		// Generate a unique filename
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		if (file.isEmpty()) {
			throw new IllegalArgumentException("File is empty");
		}

		// Specify the AWS region explicitly
		String awsRegion = "us-east-1"; // Replace with your desired region
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(awsRegion).build();

		// Upload the file to AWS S3 bucket
		try (InputStream inputStream = file.getInputStream()) {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			String s3Key = folderNamee + "/" + uniqueFileName;
			s3Client.putObject(new PutObjectRequest(bucketName, s3Key, inputStream, metadata));
		}

		// Construct and return the S3 URI
		return "https://" + bucketName + ".s3.amazonaws.com/" + folderNamee + "/" + uniqueFileName;
	}

//	
//	@GetMapping("/filesGet/{projectId}")
//	public ResponseEntity<List<FileDTO>> getFilesByProjectId(@PathVariable Long projectId) {
//		try {
//			// Check if project exists
//			Optional<PostProject> optionalPostProject = postProjectService.findById(projectId);
//			if (!optionalPostProject.isPresent()) {
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//			}
//
//			// Retrieve files for the project
//			List<ProjectFile> projectFiles = optionalPostProject.get().getFiles();
//
//			// Convert ProjectFile entities to FileDTOs
//			List<FileDTO> fileDTOs = projectFiles.stream().map(file -> new FileDTO(file.getId(), file.getFilePath()))
//					.collect(Collectors.toList());
//
//			return new ResponseEntity<>(fileDTOs, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	

	@GetMapping("/filesGet/{projectId}")
	public ResponseEntity<List<String>> getFilesByProjectId(@PathVariable Long projectId) {
		try {
			// Check if project exists
			Optional<PostProject> optionalPostProject = postProjectService.findById(projectId);
			if (!optionalPostProject.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			// Retrieve files for the project
			List<ProjectFile> projectFiles = optionalPostProject.get().getFiles();

			// Extract file paths
			List<String> filePaths = projectFiles.stream().map(ProjectFile::getFilePath).collect(Collectors.toList());

			return new ResponseEntity<>(filePaths, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getallProjects")
	public ResponseEntity<List<ProjectDTO>> getAllProjectDetails() {
		try {
			List<PostProject> projects = postProjectService.findAll();
			List<ProjectDTO> projectDetailsResponses = projects.stream().map(this::convertToProjectDetailsResponse)
					.collect(Collectors.toList());
			return ResponseEntity.ok(projectDetailsResponses);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	private ProjectDTO convertToProjectDetailsResponse(PostProject project) {
		ProjectDTO response = new ProjectDTO();
		response.setId(project.getId());
		response.setProjectTitle(project.getProject_title());
		response.setUserEmail(project.getUser().getEmail());

		response.setProjectCategory(project.getProject_category());
		response.setProject_duration(project.getProject_duration());
		response.setDeadline_date(project.getDeadline_date());
		response.setFreelancer_type(project.getFreelancer_type());
		response.setFreelancer_level(project.getFreelancer_level());
		response.setActive_rate(project.getActive_rate());
		response.setHourly_rate_from(project.getHourly_rate_from());
		response.setHourly_rate_to(project.getHourly_rate_to());
		response.setFixed_rate(project.getFixed_rate());
		response.setLanguages(project.getLanguages());
		response.setLanguage_fluency(project.getLanguage_fluency());
		response.setBudget_amount(project.getBudget_amount());
		response.setDescription(project.getDescription());
		response.setNumber_of_files(project.getNumber_of_files());
		response.setSkills(project.getSkills());
		response.setTags(project.getTags());
		response.setStatus(project.getStatus());
		response.setWorking_status(project.getWorkingstatus());

		return response;
	}

	@GetMapping("/unpublished")
	public ResponseEntity<List<Long>> getFalseStatusIds() {
		List<Long> falseStatusIds = postProjectService.findFalseStatusIds();
		return new ResponseEntity<>(falseStatusIds, HttpStatus.OK);
	}

	@PutMapping("/status/toggle/{projectId}")
	public ResponseEntity<Void> toggleStatus(@PathVariable Long projectId) {
		postProjectService.toggleStatus(projectId);
		return ResponseEntity.noContent().build();
	}
	
	 @GetMapping("/expired/{userEmail}")
	    public List<Long> getExpiredProjectIdsByUserEmail(@PathVariable String userEmail) {
	        return postProjectService.getExpiredProjectIdsByUserEmail(userEmail);
	    }

	@GetMapping("/getProjectsByIds")
	public ResponseEntity<List<ProjectDTO>> getProjectsByIds(@RequestParam List<Long> ids) {
		try {
			List<PostProject> projects = postProjectService.findByIds(ids);
			List<ProjectDTO> projectDetailsResponses = projects.stream().map(this::convertToProjectDetailsResponse)
					.collect(Collectors.toList());
			return ResponseEntity.ok(projectDetailsResponses);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/updateProject/{projectId}")
	public ResponseEntity<ProjectDTO> updateProjectDetails(@PathVariable Long projectId,
			@RequestBody ProjectDTO updatedProjectDTO) {
		try {
			Optional<PostProject> optionalProject = repooo.findById(projectId);
			if (optionalProject.isPresent()) {
				PostProject postProject = optionalProject.get();

				// Update properties only if they are not null in the updatedProjectDTO
				if (updatedProjectDTO.getDeadline_date() != null)
					postProject.setDeadline_date(updatedProjectDTO.getDeadline_date());

				// Save the updated project
				PostProject updatedProject = repooo.save(postProject);

				// Update corresponding AdminPostProject
				Optional<AdminPostProject> optionalAdminProject = adminPostProjectRpository.findById(projectId);
				if (optionalAdminProject.isPresent()) {
					AdminPostProject adminProject = optionalAdminProject.get();
					// Update only specific properties of AdminPostProject
					if (updatedProjectDTO.getDeadline_date() != null)
						adminProject.setDeadline_date(updatedProjectDTO.getDeadline_date());
					// Update other properties similarly as needed

					adminPostProjectRpository.save(adminProject);
				}

				ProjectDTO updatedProjectDTO1 = convertToProjectDetailsResponse(updatedProject);
				return ResponseEntity.ok(updatedProjectDTO1);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PatchMapping("/set-ongoing")
    public ResponseEntity<List<PostProject>> setProjectsOngoing(@RequestBody List<Long> ids) {
        List<PostProject> updatedProjects = postProjectService.updateWorkingStatusForMultiple(ids, "ongoing");
        String message = "Projects set to ongoing.";
        return ResponseEntity.ok().body(updatedProjects);
    }

    @PatchMapping("/set-complete")
    public ResponseEntity<List<PostProject>> setProjectsComplete(@RequestBody List<Long> ids) {
        List<PostProject> updatedProjects = postProjectService.updateWorkingStatusForMultiple(ids, "complete");
        String message = "Projects set to complete.";
        return ResponseEntity.ok().body(updatedProjects);
    }

	  
    
	
	    @GetMapping("/getOngoingProjectIds")
	    public ResponseEntity<List<Long>> getOngoingProjectIds() {
	        try {
	            List<Long> ongoingProjectIds = postProjectService.findProjectIdsByWorkingStatus("ongoing");
	            return ResponseEntity.ok(ongoingProjectIds);
	        } catch (Exception ex) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    @GetMapping("/getCompletedProjectIds")
	    public ResponseEntity<List<Long>> getCompletedProjectIds() {
	        try {
	            List<Long> completedProjectIds = postProjectService.findProjectIdsByWorkingStatus("complete");
	            return ResponseEntity.ok(completedProjectIds);
	        } catch (Exception ex) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
}
