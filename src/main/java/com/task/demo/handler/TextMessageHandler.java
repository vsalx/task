package com.task.demo.handler;

import com.task.demo.exception.PreconditionFailedException;

public class TextMessageHandler extends AbstractMessageHandler {

	@Override
	protected boolean validatePayload(String payload) {
		// In case of send_text the payload length should be between 1 and 160
		return !(payload.length() < 1 || payload.length() > 160);
	}

}