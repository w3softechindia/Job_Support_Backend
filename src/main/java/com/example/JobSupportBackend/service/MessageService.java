package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.Message;
import com.example.JobSupportBackend.entity.MessageCount;

public interface MessageService {

	List<Message> getMessagesBySenderAndReceiver(String sender, String receiver);

	Message saveMessage(Message message);

	MessageCount saveMessageCount(String sender, String receiver);

	Long getMessageCountBySenderAndReceiver(String sender, String receiver);
}
