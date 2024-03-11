package com.example.JobSupportBackend.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.JobSupportBackend.entity.PostProject;
import com.example.JobSupportBackend.entity.ProjectFile;
import com.example.JobSupportBackend.repo.ProjectFileRepository;
import com.example.JobSupportBackend.service.ProjectService;
import jakarta.mail.internet.ParseException;

@RestController
public class ProjectController {
	@Autowired
	private ProjectService postProjectService;

	@Autowired
	private ProjectFileRepository projectFileRepository;

//	@PostMapping("/addproject")
//	public ResponseEntity<PostProject> createProject(@RequestBody PostProject project) {
//		
//		
//	    if ("hourly".equals(project.getActive_rate())) {
//	        String hourlyRateFromString = project.getHourly_rate_from();
//	        String hourlyRateToString = project.getHourly_rate_to();
//
//	        // Check if hourly rate strings are not null or empty before parsing
//	        if (hourlyRateFromString != null && hourlyRateToString != null
//	                && !hourlyRateFromString.isEmpty() && !hourlyRateToString.isEmpty()) {
//	            try {
//	                // Convert string values to integers
//	                int hourlyRateFrom = Integer.parseInt(hourlyRateFromString);
//	                int hourlyRateTo = Integer.parseInt(hourlyRateToString);
//
//	                // Construct budget_amount for hourly rate
//	                String budgetAmount = hourlyRateFrom + " - " + hourlyRateTo;
//	                project.setBudget_amount(budgetAmount);
//	            } catch (NumberFormatException e) {
//	                // Handle the case where the input strings are not valid integers
//	                e.printStackTrace(); // Or log the error message
//	            }
//	        } else {
//	            // Handle the case where the hourly rate strings are null or empty
//	            // Log an error message or perform appropriate error handling
//	        }
//	    }
//
//	    PostProject savedProject = postProjectService.save(project);
//	    return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
//	}
//
//	  
//	  

	@PostMapping("/addproject")
	public ResponseEntity<PostProject> createProject(@RequestBody PostProject project)
			throws java.text.ParseException, ParseException {
		// Format the date to a string
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		String deadlineDateString = sdf.format(project.getDeadline_date());

		// Parse the string back to a Date object
		Date deadlineDate = sdf.parse(deadlineDateString);

		// Set the parsed date to the project
		project.setDeadline_date(deadlineDate);

		// Check if hourly rates are set
		if ("hourly".equals(project.getActive_rate())) {
			String hourlyRateFromString = project.getHourly_rate_from();
			String hourlyRateToString = project.getHourly_rate_to();

			// Check if hourly rate strings are not null or empty before parsing
			if (hourlyRateFromString != null && hourlyRateToString != null && !hourlyRateFromString.isEmpty()
					&& !hourlyRateToString.isEmpty()) {
				try {
					// Convert string values to integers
					int hourlyRateFrom = Integer.parseInt(hourlyRateFromString);
					int hourlyRateTo = Integer.parseInt(hourlyRateToString);

					// Construct budget_amount for hourly rate
					String budgetAmount = hourlyRateFrom + " - " + hourlyRateTo;
					project.setBudget_amount(budgetAmount);
				} catch (NumberFormatException e) {
					// Handle the case where the input strings are not valid integers
					e.printStackTrace(); // Or log the error message
				}
			} else {
				// Handle the case where the hourly rate strings are null or empty
				// Log an error message or perform appropriate error handling
			}
		}

		// Save the project
		PostProject savedProject = postProjectService.save(project);
		return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
	}

	@PostMapping("/files/{projectId}")
	public ResponseEntity<String> uploadFile(@PathVariable Long projectId, @RequestParam("file") MultipartFile file) {
		try {
			// Check if project exists
			Optional<PostProject> optionalPostProject = postProjectService.findById(projectId);
			if (!optionalPostProject.isPresent()) {
				return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
			}

			// Save file to local folder
			String filePath = saveFileLocally(file);

			// Save file path to database
			ProjectFile projectFile = new ProjectFile();
			projectFile.setFilePath(filePath);
			projectFile.setPostProject(optionalPostProject.get());
			projectFileRepository.save(projectFile);

			return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String saveFileLocally(MultipartFile file) throws IOException {
		String folderPath = "C:\\Users\\PURNA\\OneDrive\\Desktop\\saving files"; // Set your folder path here
		String fileName = file.getOriginalFilename();
		Path filePath = Paths.get(folderPath + File.separator + fileName);
		Files.write(filePath, file.getBytes());
		return filePath.toString();
	}

	@GetMapping
	public ResponseEntity<List<PostProject>> getAllProjects() {
		List<PostProject> projects = postProjectService.getAllProjects();
		return new ResponseEntity<>(projects, HttpStatus.OK);
	}

}
