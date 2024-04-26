package com.example.JobSupportBackend.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.entity.AdminApprovedProposal;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;

import jakarta.mail.MessagingException;

public interface AdminService {
	
	public Admin register(Admin admin) throws InvalidIdException;
	
	public Admin login(String email,String password) throws InvalidIdException;
	
	public User setStatus(String email,String status) throws ResourceNotFoundException;
	
	public String deleteUser(String email) throws InvalidIdException;
	
	public AdminApprovedProposal approveProposal(int proposalId,String proposalStatus,String approvalStatus) throws ResourceNotFoundException, InvalidIdException, Exception;
	
	public String rejectProposal(int proposalId,String proposalStatus) throws MessagingException, UnsupportedEncodingException;
	
	List<AdminApprovedProposal> getAllApprovedProposals();

	String rejectProjectToEmployer(Long projectId) throws MessagingException, UnsupportedEncodingException;
}
