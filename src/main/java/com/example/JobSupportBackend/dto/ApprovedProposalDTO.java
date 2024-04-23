package com.example.JobSupportBackend.dto;

public class ApprovedProposalDTO {

    private int id;
    private String status;
    private Long adminPostProjectId;
//    private String adminPostProjectName;
    private String freelancerId; // Assuming freelancerId is an email address
   

    // Constructors, getters, and setters

    public ApprovedProposalDTO() {
    }

    public ApprovedProposalDTO(int id, String status, Long adminPostProjectId, String adminPostProjectName, String freelancerId, String freelancerUsername) {
        this.id = id;
        this.status = status;
        this.adminPostProjectId = adminPostProjectId;
//        this.adminPostProjectName = adminPostProjectName;
        this.freelancerId = freelancerId;
      
    }

    // Getters and setters for id, status, adminPostProjectId, adminPostProjectName, freelancerId, and freelancerUsername

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAdminPostProjectId() {
        return adminPostProjectId;
    }

    public void setAdminPostProjectId(Long adminPostProjectId) {
        this.adminPostProjectId = adminPostProjectId;
    }

   

    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

   
}
