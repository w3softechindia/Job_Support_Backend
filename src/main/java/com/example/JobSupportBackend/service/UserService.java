package com.example.JobSupportBackend.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.JobSupportBackend.dto.EmployerInfo;
import com.example.JobSupportBackend.dto.Otherinfo;
import com.example.JobSupportBackend.dto.PersonalInfo;
import com.example.JobSupportBackend.dto.Register;
import com.example.JobSupportBackend.entity.AccountDeletionRequests;
import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.entity.ChartData;
import com.example.JobSupportBackend.entity.CompletedProjects;
import com.example.JobSupportBackend.entity.Portfolio;
import com.example.JobSupportBackend.entity.Review;
import com.example.JobSupportBackend.entity.SendProposal;
import com.example.JobSupportBackend.entity.Users;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;

import jakarta.mail.MessagingException;

public interface UserService {

	public Users register(Register register) throws InvalidIdException, MessagingException, UnsupportedEncodingException;

	public Users getDetails(String email);

	public List<Users> getAllUsers(String role);

	public List<Users> getAllUsersByStatus(String role, String status);

	public Users updateRole(String email, String newRole) throws Exception;

	public Users otherinfo(Otherinfo otherInfo, String email) throws Exception;

	public Users updateFreelancerDetails(String email, Users user) throws InvalidIdException;

	public Users employerInfo(EmployerInfo employerInfo, String email) throws InvalidIdException;

	public Users verifyAccount(String email, String otp) throws Exception;

	public String regenerateOtp(String email)
			throws MessagingException, InvalidIdException, UnsupportedEncodingException;

	String sendOTP(String email)
			throws InvalidIdException, MessagingException, ResourceNotFoundException, UnsupportedEncodingException;

	Boolean verifyOTP(String email, String otp) throws InvalidIdException;

	Users updatePersonalInfo(PersonalInfo personalInfo, String email) throws Exception;

	Users resetPassword(String email, String password) throws InvalidIdException;

	public Users getUserByEmail(String email);

	void updateUserImagePathAndStoreInDatabase(String email, MultipartFile file) throws IOException;

	byte[] getPhotoBytesByEmail(String email) throws IOException;

	void changePassword(String email, String password, String newPassword);

	public void postReason(String email, AccountDeletionRequests requests) throws InvalidIdException;

	public Portfolio addPortfolio(String email, Portfolio portfolio, MultipartFile multipartFile)
			throws ResourceNotFoundException, IOException;

	List<Portfolio> getAllPortfoliosWithImages(String email) throws IOException;

	public Portfolio getPortfolioByEmailAndTitle(String email, String title);

	public Portfolio updatePortfolio(String email, String title, Portfolio portfolio, MultipartFile photo)
			throws InvalidIdException, IOException, ResourceNotFoundException;

	public String deletePortfolio(String email, String title) throws ResourceNotFoundException;

	int getTotalUsersByRole(String role);

	int getActiveUsersCount(String role);

	int getDeactivatedUsersCount(String role);

	public String getUserAccountStatus(String email) throws InvalidIdException;

	public SendProposal sendProposal(long adminProjectId, String email, SendProposal proposal);

	public List<SendProposal> getProposals(String email);

	public SendProposal getProposalById(int proposalId) throws ResourceNotFoundException;

	public SendProposal updateProposals(int proposalId, SendProposal sendProposal) throws ResourceNotFoundException;

	public String deleteProposal(int proposalId);

	List<SendProposal> getProposalsByProjectId(Long id);

	public Users updateInfoForEmployeerDashBoard(String email, Users updatedUser) throws Exception;

	public void updatePhotoByEmail(String email, MultipartFile photo) throws IOException;

	public List<AdminPostProject> onGoingProjects(String email) throws ResourceNotFoundException;

	public List<CompletedProjects> completedProjects(String email);

	public List<String> getFilesByProjectId(Long projectId);

	public int getCountOfCompletedProjects(String email);

	public ChartData getChartData(String email);

	List<Users> gellallUsers();

	AdminPostProject projectStatus(String email, Long id, String status);

	public int getCountOfOngoingProjects(String email, String status);

	List<CompletedProjects> getCompletedProjectsByEmployerEmail(String email);
	
	public List<Review> getAllFreelancerReviews(String email);

}
