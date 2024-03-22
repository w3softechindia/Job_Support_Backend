package com.example.JobSupportBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	
	
	
	
	  @DeleteMapping("/removeProjectFromPublish/{projectId}")
	  
	    public ResponseEntity<String> deleteProjectById(@PathVariable Long projectId) {
	        try {
	        	idsService.deleteProjectById(projectId);
	            return new ResponseEntity<>("Project with ID " + projectId + " deleted successfully.", HttpStatus.OK);
	        } catch (IllegalArgumentException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        } catch (Exception e) {
	            return new ResponseEntity<>("An error occurred while deleting the project.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	
	
	
	
	
}
