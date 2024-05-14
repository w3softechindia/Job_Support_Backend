package com.example.JobSupportBackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminApprovedProposal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_postproject_id")
	@JsonManagedReference
	private AdminPostProject adminPostProject;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "freelancer_email")
	private User freelancer;
}
