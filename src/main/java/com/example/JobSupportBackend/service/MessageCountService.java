package com.example.JobSupportBackend.service;

import java.util.List;

import com.example.JobSupportBackend.entity.MessageCount;

public interface MessageCountService {

	Long getMessageCountBySenderAndReceiver(String sender, String receiver);

	List<MessageCount> getMessagesBySenderAndReceiver(String sender, String receiver);

	void deleteMessages(List<MessageCount> messages);
}
