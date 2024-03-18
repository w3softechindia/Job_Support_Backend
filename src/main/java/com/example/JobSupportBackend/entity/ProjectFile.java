package com.example.JobSupportBackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity

@Table(name = "project_file")
public class ProjectFile {

	public ProjectFile() {
		super();
	}

	public ProjectFile(Long id, String filePath, PostProject postProject) {
		super();
		this.id = id;
		this.filePath = filePath;
		this.postProject = postProject;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public PostProject getPostProject() {
		return postProject;
	}

	public void setPostProject(PostProject postProject) {
		this.postProject = postProject;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "file_path")
	private String filePath;

	@ManyToOne
	@JoinColumn(name = "post_project_id", referencedColumnName = "id")
	private PostProject postProject;

}
