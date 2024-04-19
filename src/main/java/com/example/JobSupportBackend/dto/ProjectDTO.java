package com.example.JobSupportBackend.dto;

import java.util.Date;
import java.util.List;

import com.example.JobSupportBackend.entity.User;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
public class ProjectDTO {

	private Long id;
	private String projectTitle;
	private String projectCategory;
	@Column(name = "project_duration")
	private String project_duration;

	private String city;
	private String state;
	private Long project_id;
	private User user;
	private Date deadline_date;

	private String freelancer_type;

	private String freelancer_level;

	private String active_rate;

	private String hourly_rate_from;

	private String hourly_rate_to;

	private Integer fixed_rate;

	private String languages;

	private String language_fluency;

	private String budget_amount;

	private String description;

	private Integer number_of_files;

	private String status;
	
	private String working_status;

	private String userEmail;

	@ElementCollection
	@CollectionTable(name = "postproject_skills", joinColumns = @JoinColumn(name = "postproject_id"))
	@Column(name = "skill")
	private List<String> skills;

	@ElementCollection
	@CollectionTable(name = "postproject_tags", joinColumns = @JoinColumn(name = "postproject_id"))
	@Column(name = "tag")
	private List<String> tags;

}
