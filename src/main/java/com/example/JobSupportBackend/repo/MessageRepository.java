package com.example.JobSupportBackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findBySenderAndReceiver(String sender, String receiver);

	Long countBySenderAndReceiver(String sender, String receiver);
}
