package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Skills;
import com.example.JobSupportBackend.entity.User;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Integer> {

	List<Skills> findByUserEmail(String email);

	Skills deleteAllByUser(User user);
	
	@Query(value="select skill from Skills skill where skill.skillName=?1")
	Skills findByName(String skillName);
  
	void deleteByUserEmail(String email);
}
