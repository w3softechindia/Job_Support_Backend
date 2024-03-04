package com.example.JobSupportBackend.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;


import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);


	
	
	
	

    


	
	 @Modifying
	    @Query(value = "UPDATE User u SET u.imageBytes = :imageBytes WHERE u.email = :email")
	    void saveImageBytes(String email, byte[] imageBytes);

	













  


}
    
   

