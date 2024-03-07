package com.example.JobSupportBackend.service;

import java.util.List;
import com.example.JobSupportBackend.entity.Language;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface LanguageService {
	void addLanguages(String userEmail, List<Language> languages) throws InvalidIdException;

}
