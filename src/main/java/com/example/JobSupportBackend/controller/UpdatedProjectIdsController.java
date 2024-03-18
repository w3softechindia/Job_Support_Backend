package com.example.JobSupportBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.service.UpdatedProjectIdsService;

@RestController
public class UpdatedProjectIdsController {

	@Autowired
	private UpdatedProjectIdsService idsService;

	@PostMapping("/updatedprojectIds")
	public ResponseEntity<String> saveUpdatedProjectIds(@RequestBody List<Long> projectIds) {
		if (projectIds.isEmpty()) {
			return ResponseEntity.badRequest().body("List of project IDs is empty.");
		}

		idsService.saveUpdatedProjectIds(projectIds);
		return ResponseEntity.status(HttpStatus.CREATED).body("Updated project IDs saved successfully.");
	}

	
	
	
	@GetMapping("/gettingupdatedprojectIds")
    public List<Long> getAllUpdatedProjectIds() {
        return idsService.getAllUpdatedProjectIds();
    }
	
	
	
	
	
	
	
	
	
	
}
