package com.example.JobSupportBackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.entity.Milestone;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.repo.AdminPostProjectRpository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.MilestoneService;

@Service
public class MilestoneServiceImpl implements MilestoneService{
	
	@Autowired
	private AdminPostProjectRpository adminPostProjectRpository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void addMilestones(long adminProjectId,String email, List<Milestone> milestones) {
		
		Optional<AdminPostProject> byId = adminPostProjectRpository.findById(adminProjectId);
		Optional<User> byId2 = userRepository.findById(email);
		
		if(byId.isPresent() && byId2.isPresent()) {
			milestones.forEach(milestone->{
				
			});
		}
	}

}
