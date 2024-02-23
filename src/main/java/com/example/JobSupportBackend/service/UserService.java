package com.example.JobSupportBackend.service;

import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.User;

public interface UserService {

	public User register(Register register);
	
	public User getDetails(String email);

	public User updateRole(String email, String newRole) throws Exception;

	public User updatePersonalInfo(PersonalInfo personalInfo, String email) throws Exception;

	public User otherinfo(Otherinfo otherInfo, String email) throws Exception;
	
	public User employerInfo(EmployerInfo employerInfo, String email) throws InvalidIdException;

}
