package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Skills;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Integer> {

	List<Skills> findByUserEmail(String email);

}
