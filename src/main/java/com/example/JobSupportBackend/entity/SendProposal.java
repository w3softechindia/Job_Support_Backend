package com.example.JobSupportBackend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SendProposal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int proposalId;
	
	private String proposedPrice;
	
	private String estimatedDelivery;
	
	private String coverLetter;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sendProposal")
	@JsonManagedReference
	private List<Milestone> milestones;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_email")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adminProjectId")
	@JsonBackReference
	private AdminPostProject adminPostProject;
}
