package com.example.JobSupportBackend.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.JobSupportBackend.entity.Photo;
import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.repo.PhotRepository;
import com.example.JobSupportBackend.repo.UserRepository;
import com.example.JobSupportBackend.service.PhotoService;

import jakarta.transaction.Transactional;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhotRepository photoRepository;

	
}
