package com.example.JobSupportBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skills {
	
	
	  @Id
	  @GeneratedValue
	 private int skillid;
	private String skills;
	private String level;
	
	@ManyToOne
	private User user; 
	
	private String degree;
	private String university;
	private String startdate;
	private String enddate;

	private String certification;
	private String certifiedfrom;
	private String year;
	private String companyname;
	private String position;
	private String companystartdate;
	private String companyenddate;

	private String language;
	private String chooselevel;
	
	

}
