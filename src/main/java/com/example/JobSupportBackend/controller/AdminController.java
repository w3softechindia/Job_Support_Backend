package com.example.JobSupportBackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.dto.ApprovedProposalDTO;
import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.entity.AdminApprovedProposal;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;
import com.example.JobSupportBackend.service.AdminService;

import jakarta.mail.MessagingException;

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/adminRegister")
	public ResponseEntity<Admin> register(@RequestBody Admin admin) throws InvalidIdException {
		return new ResponseEntity<Admin>(adminService.register(admin), HttpStatus.CREATED);
	}

	@PostMapping("/adminLogin/{email}/{password}")
	public ResponseEntity<Admin> adminLogin(@PathVariable String email, @PathVariable String password)
			throws InvalidIdException {
		return new ResponseEntity<Admin>(adminService.login(email, password), HttpStatus.OK);
	}

	@PutMapping("/changeStatus/{email}")
	public ResponseEntity<User> updateStatusByEmail(@PathVariable String email, @RequestParam String status)
			throws ResourceNotFoundException {
		User updatedUser = adminService.setStatus(email, status);
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/deleteUser/{email}")
	public ResponseEntity<String> deleteUser(@PathVariable String email) throws InvalidIdException{
		String deleteUser = adminService.deleteUser(email);
		return ResponseEntity.ok(deleteUser);
	}
	
	
	@PostMapping("/proposalApproval/{proposalId}/{proposalStatus}/{approvalStatus}")
	public ResponseEntity<AdminApprovedProposal> approval(@PathVariable int proposalId,@PathVariable String proposalStatus,@PathVariable String approvalStatus) throws Exception{
		AdminApprovedProposal approval = adminService.approveProposal(proposalId, proposalStatus, approvalStatus);
		if(approval !=null) {
			return ResponseEntity.ok(approval);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	

	
	
	

	
	
	
	
	
	@PutMapping("/rejectProposal/{proposalId}/{proposalStatus}")
	public ResponseEntity<String> rejectProposal(@PathVariable int proposalId,@PathVariable String proposalStatus ) throws MessagingException{
		String rejectProposal = adminService.rejectProposal(proposalId, proposalStatus);
		if(rejectProposal!=null) {
			return ResponseEntity.ok(rejectProposal);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
	 @GetMapping("/proposalApproval/all")
	    public ResponseEntity<List<ApprovedProposalDTO>> getAllApprovedProposals() {
	        List<AdminApprovedProposal> approvedProposals = adminService.getAllApprovedProposals();
	        List<ApprovedProposalDTO> proposalDTOs = new ArrayList<>();

	        for (AdminApprovedProposal proposal : approvedProposals) {
	            ApprovedProposalDTO dto = new ApprovedProposalDTO();
	            dto.setId(proposal.getId());
	            dto.setStatus(proposal.getStatus());
	            dto.setAdminPostProjectId(proposal.getAdminPostProject().getId());
	           
	            dto.setFreelancerId(proposal.getFreelancer().getEmail());
	            
	            // Set other properties as needed

	            proposalDTOs.add(dto);
	        }

	        if (!proposalDTOs.isEmpty()) {
	            return ResponseEntity.ok(proposalDTOs);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	
	
	
	


}
