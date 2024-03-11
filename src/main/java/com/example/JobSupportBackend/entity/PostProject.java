package com.example.JobSupportBackend.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "postproject")
public class PostProject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	

	
	@Column(name = "project_title")
	private String project_title;

	@Column(name = "project_category")
	private String project_category;

	@Column(name = "project_duration")
	private String project_duration;

	@Column(name = "deadline_date")
//	    @Temporal(TemporalType.TIMESTAMP) // Adjust according to the temporal type
	  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date deadline_date;
	
	
	

	@Column(name = "freelancer_type")
	private String freelancer_type;

	@Column(name = "freelancer_level")
	private String freelancer_level;

	@Column(name = "active_rate")
	private String active_rate;

	@Column(name = "hourly_rate_from")
	private String hourly_rate_from;

	@Column(name = "hourly_rate_to")
	private String hourly_rate_to;

	@Column(name = "fixed_rate")
	private Integer fixed_rate;

	@Column(name = "languages")
	private String languages;

	@Column(name = "language_fluency")
	private String language_fluency;

	@Column(name = "budget_amount")
	private String budget_amount;

	@Column(name = "description", length = 1000) // adjust the length as needed
	private String description;

	@Column(name = "number_of_files")
	private Integer number_of_files;

	

	@ElementCollection
	    @CollectionTable(name = "postproject_skills", joinColumns = @JoinColumn(name = "postproject_id"))
	    @Column(name = "skill")
	    private List<String> skills;

	@ElementCollection
	    @CollectionTable(name = "postproject_tags", joinColumns = @JoinColumn(name = "postproject_id"))
	    @Column(name = "tag")
	    private List<String> tags;
	
	
	  @OneToMany(mappedBy = "postProject", cascade = CascadeType.ALL)
	    private List<ProjectFile> files;
	
	
	
	

}
