package com.example.JobSupportBackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JobSupportBackend.entity.MessageCount;
import com.example.JobSupportBackend.repo.MessageCountRepository;
import com.example.JobSupportBackend.service.MessageCountService;

@Service
public class MessageCountServiImpl implements MessageCountService {

	@Autowired
	private MessageCountRepository messageCountRepository;

	@Override
	public Long getMessageCountBySenderAndReceiver(String sender, String receiver) {
		return messageCountRepository.countBySenderAndReceiver(sender, receiver);
	}

	@Override
	public List<MessageCount> getMessagesBySenderAndReceiver(String sender, String receiver) {
		return messageCountRepository.findBySenderAndReceiver(sender, receiver);
	}

	@Override
	public void deleteMessages(List<MessageCount> messages) {
		messageCountRepository.deleteAll(messages);
	}
}
