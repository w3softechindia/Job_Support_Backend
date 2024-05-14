package com.example.JobSupportBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.entity.MessageCount;
import com.example.JobSupportBackend.service.MessageCountService;

@RestController
public class MessgaeCountController {

	@Autowired
	MessageCountService messageCountService;

	@GetMapping("/message/count")
	public ResponseEntity<Long> getMessageCountBySenderAndReceiver(@RequestParam String sender,
			@RequestParam String receiver) {
		Long messageCount = messageCountService.getMessageCountBySenderAndReceiver(sender, receiver);
		return ResponseEntity.ok(messageCount);
	}

	@DeleteMapping("/message/delete")
	public ResponseEntity<String> deleteMessagesBySenderAndReceiver(@RequestParam String sender,
			@RequestParam String receiver) {
		try {
			List<MessageCount> messagesToDelete = messageCountService.getMessagesBySenderAndReceiver(sender, receiver);
			messageCountService.deleteMessages(messagesToDelete);
			return ResponseEntity.status(HttpStatus.OK)
					.body("Messages deleted for sender: " + sender + " and receiver: " + receiver);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting messages.");
		}
	}

}
