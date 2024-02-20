package com.example.JobSupportBackend.service;

import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.dto.Role;
import com.example.JobSupportBackend.dto.SkillsAndExperience;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface UserService {

	public User register(Register register);

	public User updateRole(User user, String email) throws Exception;


	public User personalInfo(PersonalInfo personalInfo, String email) throws Exception;

	public User skillsandexperience( String email) throws InvalidIdException;

	public User otherinfo(Otherinfo otherInfo, String email) throws Exception;
	

}
