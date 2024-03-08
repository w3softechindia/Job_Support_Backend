package com.example.JobSupportBackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.PostProject;

@Repository
public interface ProjectRepo  extends JpaRepository<PostProject, Long>{

}
