package com.example.JobSupportBackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.entity.AdminApprovedProposal;
import com.example.JobSupportBackend.entity.AdminPostProject;
import com.example.JobSupportBackend.entity.SendProposal;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.exceptions.ResourceNotFoundException;
import com.example.JobSupportBackend.repo.AdminApprovedProposalRepository;
import com.example.JobSupportBackend.repo.AdminPostProjectRpository;
import com.example.JobSupportBackend.repo.AdminRepository;
import com.example.JobSupportBackend.repo.CertificationRepository;
import com.example.JobSupportBackend.repo.DeletedAccountsRepository;
import com.example.JobSupportBackend.repo.EducationRepository;
import com.example.JobSupportBackend.repo.ExperienceRepository;
import com.example.JobSupportBackend.repo.LanguageRepository;
import com.example.JobSupportBackend.repo.PortfolioRepository;
import com.example.JobSupportBackend.repo.ProposalsRepository;
import com.example.JobSupportBackend.repo.SkillsRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.AdminService;

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
	private DeletedAccountsRepository accountsRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private AdminPostProjectRpository adminPostProjectRpository;

	@Autowired
	private AdminApprovedProposalRepository adminApprovedProposalRepository;

	@Autowired
	private ProposalsRepository proposalsRepository;

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
	public User setStatus(String email, String status) throws ResourceNotFoundException {
		User byEmail = userRepository.findByEmail(email);
		if (byEmail != null) {
			byEmail.setStatus(status);
			return userRepository.save(byEmail);
		} else {
			throw new ResourceNotFoundException("Email Not Found..!!!" + email);
		}
	}

	@Override
	public String deleteUser(String email) throws InvalidIdException {
		User byEmail = userRepository.findByEmail(email);
		if (byEmail != null) {
			skillsRepository.deleteByUserEmail(email);
			educationRepository.deleteByUserEmail(email);
			certificationRepository.deleteByUserEmail(email);
			experienceRepository.deleteByUserEmail(email);
			languageRepository.deleteByUserEmail(email);
			accountsRepository.deleteById(email);
			portfolioRepository.deleteByUserEmail(email);

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
		approvedProposal.setFreelancer(sendProposal.getUser()); // Assuming the User is the freelancer
		approvedProposal.setAdminPostProject(project);

		adminApprovedProposalRepository.save(approvedProposal);
//		project.setAdminApprovedProposal(approvedProposal);
		adminPostProjectRpository.save(project);

		sendProposal.setProposalStatus(proposalStatus); // Dynamic acceptance status
		proposalsRepository.save(sendProposal);

		return approvedProposal;
	}

	@Override
	public String rejectProposal(int proposalId, String proposalStatus) {
	    Optional<SendProposal> proposalOpt = proposalsRepository.findById(proposalId);
	    
	    if (!proposalOpt.isPresent()) {
	        return "Proposal Not Found";
	    }

	    SendProposal proposal = proposalOpt.get();
	    proposal.setProposalStatus(proposalStatus);
	    proposalsRepository.save(proposal);

	    // Assuming the freelancer's email can be fetched like this
	    String freelancerEmail = proposal.getUser().getEmail();

	    // Retrieve the approved proposal linked to this freelancer's email
	    Optional<AdminApprovedProposal> approvedProposalOpt = adminApprovedProposalRepository.findByFreelancerEmail(freelancerEmail);

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

	  
	


}
