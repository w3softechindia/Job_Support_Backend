package com.example.JobSupportBackend.service;

import java.util.Set;

import com.example.JobSupportBackend.entity.Language;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;

public interface LanguageService {

	User getUserByEmail(String email);

	void addLanguages(String userEmail, Set<Language> languages) throws InvalidIdException;

}
