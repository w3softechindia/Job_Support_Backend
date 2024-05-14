package com.example.JobSupportBackend.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.JobSupportBackend.entity.MessageCount;

@Repository
public interface MessageCountRepository extends JpaRepository<MessageCount, Long> {

	Long countBySenderAndReceiver(String sender, String receiver);

	void deleteBySenderAndReceiver(String sender, String receiver);

	List<MessageCount> findBySenderAndReceiver(String sender, String receiver);
}
