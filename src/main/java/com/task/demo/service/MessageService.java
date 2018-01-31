package com.task.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.demo.domain.Message;
import com.task.demo.enums.MessageType;
import com.task.demo.exception.PreconditionFailedException;
import com.task.demo.handler.AbstractMessageHandler;
import com.task.demo.handler.EmotionMessageHandler;
import com.task.demo.handler.TextMessageHandler;
import com.task.demo.repo.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	public void sendMessage(final MessageType type, final String payload) throws PreconditionFailedException {
		AbstractMessageHandler handler= null;
		switch (type) {
		case send_text:
			handler = new TextMessageHandler();
			break;
		case send_emotion:
			handler = new EmotionMessageHandler();
		}
		Message message = handler.createMessage(type, payload);
		messageRepository.save(message);
	}
}
