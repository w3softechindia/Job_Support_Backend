package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.Milestone;

public interface MilestoneService {
	void addMilestones(long adminProjectId,String email,List<Milestone> milestones);
}
