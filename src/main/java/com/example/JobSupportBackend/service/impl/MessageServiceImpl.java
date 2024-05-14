package com.example.JobSupportBackend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.Message;
import com.example.JobSupportBackend.entity.MessageCount;
import com.example.JobSupportBackend.repo.MessageCountRepository;
import com.example.JobSupportBackend.repo.MessageRepository;
import com.example.JobSupportBackend.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MessageCountRepository meesagecountrepository;

	private final Map<String, Long> messageCounts = new ConcurrentHashMap<>();

	@Override
	public List<Message> getMessagesBySenderAndReceiver(String sender, String receiver) {
		return messageRepository.findBySenderAndReceiver(sender, receiver);
	}

	@Override
	public Message saveMessage(Message message) {
		message.setTimestamp(LocalDateTime.now()); // Set current timestamp
		return messageRepository.save(message);
	}

	@Override
	public MessageCount saveMessageCount(String sender, String receiver) {
		Long count = messageRepository.countBySenderAndReceiver(sender, receiver);
		MessageCount messageCount = new MessageCount();
		messageCount.setSender(sender);
		messageCount.setReceiver(receiver);
		messageCount.setMessageCount(count);
		return meesagecountrepository.save(messageCount);
	}

	@Override
	public Long getMessageCountBySenderAndReceiver(String sender, String receiver) {
		return messageRepository.countBySenderAndReceiver(sender, receiver);
	}

}
