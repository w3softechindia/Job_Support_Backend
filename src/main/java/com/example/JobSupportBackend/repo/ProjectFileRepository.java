package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.ProjectFile;
@Repository
public interface ProjectFileRepository  extends JpaRepository<ProjectFile, Long>{

	List<ProjectFile> findByPostProjectId(Long projectId);

}
