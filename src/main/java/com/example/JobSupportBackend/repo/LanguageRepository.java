package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer>{

	List<Language> findByUserEmail(String email);
}
