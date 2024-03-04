package com.example.JobSupportBackend.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;

import jakarta.mail.MessagingException;

public interface UserService {

	public User register(Register register) throws InvalidIdException, MessagingException;

	public User getDetails(String email);

	public User updateRole(String email, String newRole) throws Exception;

	public User otherinfo(Otherinfo otherInfo, String email) throws Exception;

	public User employerInfo(EmployerInfo employerInfo, String email) throws InvalidIdException;

	public User verifyAccount(String email, String otp) throws Exception;

	public String regenerateOtp(String email) throws MessagingException, InvalidIdException;

	User sendOTP(String email) throws InvalidIdException, MessagingException, ResourceNotFoundException;

	boolean verifyOTP(String email, String otp) throws InvalidIdException;

	User updatePersonalInfo(PersonalInfo personalInfo, String email) throws Exception;

	User resetPassword(String email, String password) throws InvalidIdException;

	public User getUserByEmail(String email);

	void updateUserImagePathAndStoreInDatabase(String email, MultipartFile file) throws IOException;

	byte[] getPhotoBytesByEmail(String email) throws IOException;
}
