package com.task.demo.handler;

import com.task.demo.domain.Message;
import com.task.demo.enums.MessageType;
import com.task.demo.exception.PreconditionFailedException;

public abstract class AbstractMessageHandler {
	
	public Message createMessage(MessageType type, String payload) throws PreconditionFailedException {
		if(!validatePayload(payload)) {
			throw new PreconditionFailedException();
		}
		Message message = new Message(type, payload);		
		return message;
	}
	protected abstract boolean validatePayload(String payload);
}
