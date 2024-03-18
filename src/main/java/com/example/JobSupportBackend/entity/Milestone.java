package com.example.JobSupportBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Milestone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int milestoneId;
	
	private String milestoneName;
	
	private String price;
	
	private String startdate;
	
	private String enddate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proposal_id")
	@JsonBackReference
	private SendProposal sendProposal;
}
