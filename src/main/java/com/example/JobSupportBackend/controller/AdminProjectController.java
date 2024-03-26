package com.example.JobSupportBackend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.dto.ProjectDTO;
import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.repo.AdminPostProjectRpository;
import com.example.JobSupportBackend.service.AdminProjectService;

@RestController
public class AdminProjectController {

	@Autowired
	private AdminPostProjectRpository repoo;
	
	@Autowired
	private AdminProjectService adminProjectService;

	@GetMapping("/getAllAdminProjects")
	public ResponseEntity<List<ProjectDTO>> getAllProjectDetails() {
		try {
			List<AdminPostProject> projects = repoo.findAll();
			List<ProjectDTO> projectDetailsResponses = projects.stream().map(this::convertToProjectDetailsResponse)
					.collect(Collectors.toList());
			return ResponseEntity.ok(projectDetailsResponses);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	private ProjectDTO convertToProjectDetailsResponse(AdminPostProject adminPostProject) {
		ProjectDTO projectDTO = new ProjectDTO();
		// Populate projectDTO with data from adminPostProject

		projectDTO.setId(adminPostProject.getId());
		projectDTO.setProject_id(adminPostProject.getProject_id());
		projectDTO.setProjectTitle(adminPostProject.getProject_title());
		projectDTO.setProjectCategory(adminPostProject.getProject_category());
		projectDTO.setProject_duration(adminPostProject.getProject_duration());
		projectDTO.setDeadline_date(adminPostProject.getDeadline_date());
		projectDTO.setFreelancer_type(adminPostProject.getFreelancer_type());
		projectDTO.setFreelancer_level(adminPostProject.getFreelancer_level());
		projectDTO.setActive_rate(adminPostProject.getActive_rate());
		projectDTO.setHourly_rate_from(adminPostProject.getHourly_rate_from());
		projectDTO.setHourly_rate_to(adminPostProject.getHourly_rate_to());
		projectDTO.setFixed_rate(adminPostProject.getFixed_rate());
		projectDTO.setLanguages(adminPostProject.getLanguages());
		projectDTO.setLanguage_fluency(adminPostProject.getLanguage_fluency());
		projectDTO.setBudget_amount(adminPostProject.getBudget_amount());
		projectDTO.setDescription(adminPostProject.getDescription());
		projectDTO.setNumber_of_files(adminPostProject.getNumber_of_files());
		projectDTO.setSkills(adminPostProject.getSkills());
		projectDTO.setTags(adminPostProject.getTags());
		projectDTO.setUserEmail(adminPostProject.getUser().getEmail());
		projectDTO.setCity(adminPostProject.getUser().getCity());
		projectDTO.setState(adminPostProject.getUser().getState());
		return projectDTO;
	}

	@GetMapping("/getAdminProjectById/{projectId}")
	public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long projectId) {
		try {
			Optional<AdminPostProject> optionalProject = repoo.findById(projectId);
			if (optionalProject.isPresent()) {
				AdminPostProject adminPostProject = optionalProject.get();
				ProjectDTO projectDTO = convertToProjectDetailsResponse(adminPostProject);
				return ResponseEntity.ok(projectDTO);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/updateAdminProject/{projectId}")
	public ResponseEntity<ProjectDTO> updateProjectDetails(@PathVariable Long projectId,
			@RequestBody ProjectDTO updatedProjectDTO) {
		try {
			Optional<AdminPostProject> optionalProject = repoo.findById(projectId);
			if (optionalProject.isPresent()) {
				AdminPostProject adminPostProject = optionalProject.get();

				// Update properties only if they are not null in the updatedProjectDTO
				if (updatedProjectDTO.getProjectTitle() != null)
					adminPostProject.setProject_title(updatedProjectDTO.getProjectTitle());
				if (updatedProjectDTO.getProjectCategory() != null)
					adminPostProject.setProject_category(updatedProjectDTO.getProjectCategory());
				// Repeat this for other properties...

				if (updatedProjectDTO.getBudget_amount() != null)
					adminPostProject.setBudget_amount(updatedProjectDTO.getBudget_amount());

				if (updatedProjectDTO.getDeadline_date() != null)
					adminPostProject.setDeadline_date(updatedProjectDTO.getDeadline_date());

				// Save the updated project
				AdminPostProject updatedProject = repoo.save(adminPostProject);

				// Convert to DTO and return
				ProjectDTO updatedProjectDTO1 = convertToProjectDetailsResponse(updatedProject);
				return ResponseEntity.ok(updatedProjectDTO1);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/getProjectById/{id}")
	public ResponseEntity<AdminPostProject> getProjectById(@PathVariable long id){
		AdminPostProject projectById = adminProjectService.getProjectById(id);
		return ResponseEntity.ok(projectById);
	}
	
	@GetMapping("/getProjectsOfAdmin")
	public ResponseEntity<List<AdminPostProject>> getAll(){
		List<AdminPostProject> allProjects = adminProjectService.getAllProjects();
		return new ResponseEntity<List<AdminPostProject>>(allProjects,HttpStatus.OK);
	}
}
