package com.example.JobSupportBackend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.DeletedAccounts;
import com.example.JobSupportBackend.entity.Portfolio;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;

import jakarta.mail.MessagingException;

public interface UserService {

	public User register(Register register) throws InvalidIdException, MessagingException;

	public User getDetails(String email);

	public User updateRole(String email, String newRole) throws Exception;

	public User otherinfo(Otherinfo otherInfo, String email) throws Exception;

	public User updateFreelancerDetails(String email, User user) throws InvalidIdException;

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

	void changePassword(String email, String password, String newPassword);

	public void postReason(String email, DeletedAccounts deletedAccounts) throws InvalidIdException;

	public Portfolio addPortfolio(String email, Portfolio portfolio, MultipartFile multipartFile)
			throws ResourceNotFoundException, IOException;

	public List<Portfolio> getPortfoliosByEmail(String email);
	
	public Portfolio getPortfolioByEmailAndTitle(String email,String title);
	
	public Portfolio updatePortfolio(String email,String title,Portfolio portfolio,MultipartFile photo) throws InvalidIdException, IOException;
	
	public String deletePortfolio(String email,String title) throws ResourceNotFoundException;

//	 byte[] getImageDataByEmail(String email) throws IOException;
}
