package com.task.demo.handler;

public class EmotionMessageHandler extends AbstractMessageHandler {

	@Override
	protected boolean validatePayload(String payload) {
		// In case of send_emotion the payload should be between 2 and 10 and should not
		// contain characters between 0 and 9
		return !(payload.length() < 2 || payload.length() > 10 || payload.matches(".*\\d+.*"));
	}

}
