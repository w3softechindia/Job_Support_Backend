package com.example.JobSupportBackend.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletedProjects {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String employer;

	private String freelancer;
	
	private Long project_id;

	@Column(name = "project_title")
	private String project_title;

	@Column(name = "project_category")
	private String project_category;

	@Column(name = "project_duration")
	private String project_duration;

	@Column(name = "deadline_date")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date deadline_date;

	@Column(name = "active_rate")
	private String active_rate;

	@Column(name = "hourly_rate_from")
	private String hourly_rate_from;

	@Column(name = "hourly_rate_to")
	private String hourly_rate_to;

	@Column(name = "fixed_rate")
	private Integer fixed_rate;

	@Column(name = "budget_amount")
	private String budget_amount;

	private String project_status;

	@Column(name = "completion_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completionDate = new Date();
}
