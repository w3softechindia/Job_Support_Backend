package com.example.JobSupportBackend.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.EmailUtil.EmailUtil;
import com.example.JobSupportBackend.entity.AccountDeletionRequests;
import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.entity.AdminApprovedProposal;
import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.entity.DeletedAccounts;
import com.example.JobSupportBackend.entity.PostProject;
import com.example.JobSupportBackend.entity.SendProposal;
import com.example.JobSupportBackend.entity.Users;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;
import com.example.JobSupportBackend.repo.AccountDeletionRequestsRepository;
import com.example.JobSupportBackend.repo.AdminApprovedProposalRepository;
import com.example.JobSupportBackend.repo.AdminPostProjectRpository;
import com.example.JobSupportBackend.repo.AdminRepository;
import com.example.JobSupportBackend.repo.CertificationRepository;
import com.example.JobSupportBackend.repo.EducationRepository;
import com.example.JobSupportBackend.repo.ExperienceRepository;
import com.example.JobSupportBackend.repo.LanguageRepository;
import com.example.JobSupportBackend.repo.PortfolioRepository;
import com.example.JobSupportBackend.repo.ProjectRepo;
import com.example.JobSupportBackend.repo.ProposalsRepository;
import com.example.JobSupportBackend.repo.SkillsRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.AdminService;

import jakarta.mail.MessagingException;

@Service
public class AdminServiceImple implements AdminService {

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private SkillsRepository skillsRepository;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private CertificationRepository certificationRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private AccountDeletionRequestsRepository accountsRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private AdminPostProjectRpository adminPostProjectRpository;

	@Autowired
	private AdminApprovedProposalRepository adminApprovedProposalRepository;

	@Autowired
	private ProposalsRepository proposalsRepository;
	
	@Autowired
	private ProjectRepo projectRepo;

	@Autowired
	private EmailUtil emailUtil;

	@Override
	public Admin register(Admin admin) throws InvalidIdException {
		return adminRepository.save(admin);
	}

	@Override
	public Admin login(String email, String password) throws InvalidIdException {
		Admin admin = adminRepository.findById(email)
				.orElseThrow(() -> new InvalidIdException("Email Doesn't exist...!!!" + email));
		if (admin.getPassword().equals(password)) {
			return admin;
		} else {
			throw new InvalidIdException("Invalid Password...!!!");
		}
	}

	@Override
	public Users setStatus(String email, String status) throws ResourceNotFoundException {
		Users byEmail = userRepository.findByEmail(email);
		if (byEmail != null) {
			byEmail.setStatus(status);
			return userRepository.save(byEmail);
		} else {
			throw new ResourceNotFoundException("Email Not Found..!!!" + email);
		}
	}

	@Override
	public String deleteUser(String email) throws InvalidIdException {
		Users byEmail = userRepository.findByEmail(email);
		if (byEmail != null) {
			skillsRepository.deleteByUsersEmail(email);
			educationRepository.deleteByUsersEmail(email);
			certificationRepository.deleteByUsersEmail(email);
			experienceRepository.deleteByUsersEmail(email);
			languageRepository.deleteByUsersEmail(email);
			accountsRepository.deleteById(email);
			portfolioRepository.deleteByUsersEmail(email);

			userRepository.delete(byEmail);

			return "User Account Deleted Successfully..!!";
		} else {
			throw new InvalidIdException("Email not Found..!!!" + email);
		}
	}

	@Override
	public AdminApprovedProposal approveProposal(int proposalId, String proposalStatus, String approvalStatus)
			throws Exception {

		SendProposal sendProposal = proposalsRepository.findById(proposalId)
				.orElseThrow(() -> new Exception("Proposal not found"));

		AdminPostProject project = sendProposal.getAdminPostProject();

		AdminApprovedProposal approvedProposal = new AdminApprovedProposal();
		approvedProposal.setStatus(approvalStatus); // Dynamic status
		approvedProposal.setFreelancer(sendProposal.getUsers()); // Assuming the User is the freelancer
		approvedProposal.setAdminPostProject(project);

		adminApprovedProposalRepository.save(approvedProposal);
//		project.setAdminApprovedProposal(approvedProposal);
		adminPostProjectRpository.save(project);
		
		emailUtil.sendFreelancerHiringNotification(sendProposal.getUsers().getEmail(), project.getProject_title());
		emailUtil.sendProjectStartedNotification(project.getUser().getEmail(), sendProposal.getUsers().getName(), project.getProject_title());

		sendProposal.setProposalStatus(proposalStatus); // Dynamic acceptance status
		proposalsRepository.save(sendProposal);

		return approvedProposal;
	}

	@Override
	public String rejectProposal(int proposalId, String proposalStatus) throws MessagingException, UnsupportedEncodingException {
		Optional<SendProposal> proposalOpt = proposalsRepository.findById(proposalId);

		if (!proposalOpt.isPresent()) {
			return "Proposal Not Found";
		}

		SendProposal proposal = proposalOpt.get();
		proposal.setProposalStatus(proposalStatus);
		proposalsRepository.save(proposal);

		// Assuming the freelancer's email can be fetched like this
		String freelancerEmail = proposal.getUsers().getEmail();
		
		emailUtil.sendRejectionNotification(freelancerEmail, proposal.getAdminPostProject().getProject_title());

		// Retrieve the approved proposal linked to this freelancer's email
		Optional<AdminApprovedProposal> approvedProposalOpt = adminApprovedProposalRepository
				.findFirstByFreelancer_Email(freelancerEmail);

		// If an approved proposal is found, delete it
		approvedProposalOpt.ifPresent(approvedProposal -> {
			adminApprovedProposalRepository.delete(approvedProposal);
		});
		return "Proposal Rejected Successfully..!!!";
	}
	
	@Override
	public List<AdminApprovedProposal> getAllApprovedProposals() {
	    // Assuming adminApprovedProposalRepository is the repository for AdminApprovedProposal
	    return adminApprovedProposalRepository.findAll();
	}

	@Override
	public String rejectProjectToEmployer(Long projectId) throws MessagingException, UnsupportedEncodingException {
		PostProject postProject = projectRepo.findById(projectId).get();
		if(postProject!=null) {
			emailUtil.sendProjectRejectionToEmployer(postProject.getUsers().getEmail(), postProject.getProject_title());
			return "Project Rejected Succesfully";
		}
		return "No Projects Found";
	}

	@Override
	public String deleteAccount(String email,String status) throws InvalidIdException {
//		User user = userRepository.findById(email).orElseThrow(()->new InvalidIdException("Employee Id not found...!!!!"));
		AccountDeletionRequests requests = accountsRepository.findById(email).orElseThrow(()->new InvalidIdException("Employee Id not found...!!!!"));
		DeletedAccounts deletedAccounts=new DeletedAccounts();
		deletedAccounts.setEmail(requests.getEmail());
		deletedAccounts.setReason(requests.getReason());
		deletedAccounts.setStatus(status);
		
		return "Deleted Successfully....!!!!!";
	}

}
