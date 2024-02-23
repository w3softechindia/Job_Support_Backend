package com.example.JobSupportBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployerInfo {
	
	private String ecompany;
	private String etagline;
	private String establishdate;
	private String ecompanyownername;
	private String industry;
	private String ewebsite;
	private String eteamsize;
	private String edescribe;
}
