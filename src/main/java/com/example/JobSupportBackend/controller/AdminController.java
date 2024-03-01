package com.example.JobSupportBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.entity.Admin;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.service.AdminService;

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/adminRegister")
	public ResponseEntity<Admin> register(@RequestBody Admin admin) throws InvalidIdException {
		return new ResponseEntity<Admin>(adminService.register(admin), HttpStatus.CREATED);
	}
}
