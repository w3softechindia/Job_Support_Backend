package com.example.JobSupportBackend.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Language;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.LanguageRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.LanguageService;

@Service
public class LanguageServiceImple implements LanguageService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LanguageRepository languageRepository;


	@Override
	public void addLanguages(String userEmail, List<Language> languages) throws InvalidIdException {
		User user = userRepository.findByEmail(userEmail);
		if(user!=null) {
			languages.forEach(language->{
				language.setUser(user);
				languageRepository.save(language);
			});
		}else {
			throw new InvalidIdException("Email not found..!!"+userEmail);
		}
	}

}
