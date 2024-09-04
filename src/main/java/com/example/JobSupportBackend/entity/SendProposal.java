package com.example.JobSupportBackend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	private String proposalStatus;

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sendProposal")
	@JsonManagedReference
	private List<Milestone> milestones;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Users users;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private AdminPostProject adminPostProject;
}
