package com.example.JobSupportBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
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
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int portfolio_Id;
	private String title;
	private String link;
	private String photo_path;
	
	@Column(name = "image_bytes", columnDefinition = "LONGBLOB")
	private byte[] imageBytes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_email")
	@JsonBackReference
	private User user;

}
