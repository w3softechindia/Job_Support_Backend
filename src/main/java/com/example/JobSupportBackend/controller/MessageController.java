package com.example.JobSupportBackend.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.JobSupportBackend.entity.Message;
import com.example.JobSupportBackend.entity.MessageCount;
import com.example.JobSupportBackend.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	private MessageService messageService;

	@GetMapping("gett/{sender}/{receiver}")
	public ResponseEntity<List<Map<String, String>>> getMessagesBySenderAndReceiver(@PathVariable String sender,
			@PathVariable String receiver) {
		List<Message> senderMessages = messageService.getMessagesBySenderAndReceiver(sender, receiver);
		List<Message> receiverMessages = messageService.getMessagesBySenderAndReceiver(receiver, sender);

		// Combine sender and receiver messages
		List<Map<String, String>> messages = new ArrayList<>();

		// Add messages to the response, marking sender's messages as "right" and
		// receiver's as "left"
		for (Message message : senderMessages) {
			Map<String, String> messageMap = new HashMap<>();
			messageMap.put("content", message.getContent());
			messageMap.put("timestamp", message.getTimestamp().toString());
			messageMap.put("side", "right"); // Sender's messages are on the right
			messages.add(messageMap);
		}

		for (Message message : receiverMessages) {
			Map<String, String> messageMap = new HashMap<>();
			messageMap.put("content", message.getContent());
			messageMap.put("timestamp", message.getTimestamp().toString());
			messageMap.put("side", "left"); // Receiver's messages are on the left
			messages.add(messageMap);
		}

		// Sort messages by timestamp
		messages.sort(Comparator.comparing(m -> LocalDateTime.parse(m.get("timestamp"))));

		return ResponseEntity.ok(messages);
	}

//    @PostMapping("/send")
//    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
//        Message savedMessage = messageService.saveMessage(message);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
//    }

	@PostMapping("/send")
	public ResponseEntity<Message> createMessage(@RequestBody Message message) {
		Message savedMessage = messageService.saveMessage(message);
		// Save message count
		MessageCount savedMessageCount = messageService.saveMessageCount(message.getSender(), message.getReceiver());
		return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
	}

//       
//    @GetMapping("/count")
//    public ResponseEntity<Long> getMessageCountBySenderAndReceiver(
//        @RequestParam String sender,
//        @RequestParam String receiver
//    ) {
//        Long messageCount = messageService.getMessageCountBySenderAndReceiver(sender, receiver);
//        return ResponseEntity.ok(messageCount);
//    }
//    
//    
//    

	@GetMapping("/count")
	public ResponseEntity<Long> getMessageCountBySenderAndReceiver(@RequestParam String sender,
			@RequestParam String receiver) {
		Long messageCount = messageService.getMessageCountBySenderAndReceiver(sender, receiver);
		return ResponseEntity.ok(messageCount);
	}

}
