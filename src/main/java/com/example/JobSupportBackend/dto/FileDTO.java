package com.example.JobSupportBackend.dto;

import lombok.Data;

@Data
public class FileDTO {

	private Long id;
	private String filePath;
	
	
	
	 public FileDTO(Long id, String filePath) {
	        this.id = id;
	        this.filePath = filePath;
	    }

}
