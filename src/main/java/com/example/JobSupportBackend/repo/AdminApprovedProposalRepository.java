package com.example.JobSupportBackend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.AdminApprovedProposal;

@Repository
public interface AdminApprovedProposalRepository extends JpaRepository<AdminApprovedProposal, Integer> {

	Optional<AdminApprovedProposal> findFirstByFreelancer_Email(String email); // Get the first match, if that's the														

	List<AdminApprovedProposal> findByFreelancer_Email(String email);

    @Query("SELECT COUNT(a) FROM AdminApprovedProposal a WHERE a.freelancer.email = :email AND a.adminPostProject.project_status = 'Completed'")
	int countByFreelancerEmailAndCompletedStatus(@Param("email") String email);
    
    @Query("SELECT COUNT(a) FROM AdminApprovedProposal a WHERE a.freelancer.email = :email AND a.adminPostProject.project_status = 'Pending'")
   	int countByFreelancerEmailAndPendingStatus(@Param("email") String email);

}
