package com.task.demo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.task.demo.domain.Message;
import com.task.demo.enums.MessageType;
import com.task.demo.exception.PreconditionFailedException;
import com.task.demo.repo.MessageRepository;
import com.task.demo.service.MessageService;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class MessageServiceUnitTests {

	@Configuration
	static class AccountServiceTestContextConfiguration {
		@Bean
		public MessageService messageService() {
			return new MessageService();
		}
	}

	@Autowired
	MessageService messageService;

	@MockBean
	private MessageRepository messageRepository;
	
	private final static String TOO_LONG_PAYLOAD = RandomStringUtils.randomAlphabetic(161);
	private final static String TOO_SHORT_PAYLOAD = "";
	private final static String ALPHANUMERIC_PAYLOAD = "text123";
	private final static String CORRECT_PAYLOAD = "correct :)";
	
	@Test
	public void testSendTextMessageCorrect() {
		Message expected = new Message(MessageType.send_text, CORRECT_PAYLOAD);
		
		messageService.sendMessage(MessageType.send_text, CORRECT_PAYLOAD);
		ArgumentCaptor<Message> messageArgument = ArgumentCaptor.forClass(Message.class);
		verify(messageRepository, times(1)).save(messageArgument.capture());
		
		Message actual = messageArgument.getValue();
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
	
	@Test
	public void testSendEmotionMessageCorrect() {
		Message expected = new Message(MessageType.send_emotion, CORRECT_PAYLOAD);
		
		messageService.sendMessage(MessageType.send_emotion, CORRECT_PAYLOAD);
		ArgumentCaptor<Message> messageArgument = ArgumentCaptor.forClass(Message.class);
		verify(messageRepository, times(1)).save(messageArgument.capture());
		
		Message actual = messageArgument.getValue();
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
	
	@Test
	public void testSendTextMessageAlphaNumericCorrect() {
		Message expected = new Message(MessageType.send_text, ALPHANUMERIC_PAYLOAD);
		
		messageService.sendMessage(MessageType.send_text, ALPHANUMERIC_PAYLOAD);
		ArgumentCaptor<Message> messageArgument = ArgumentCaptor.forClass(Message.class);
		verify(messageRepository, times(1)).save(messageArgument.capture());
		
		Message actual = messageArgument.getValue();
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
	
	@Test(expected = PreconditionFailedException.class)
	public void testSendTextMessageTooLong() {
		messageService.sendMessage(MessageType.send_text, TOO_LONG_PAYLOAD);
	}
	
	@Test(expected = PreconditionFailedException.class)
	public void testSendTextMessageTooShort() {
		messageService.sendMessage(MessageType.send_text, TOO_SHORT_PAYLOAD);
	}
	
	@Test(expected = PreconditionFailedException.class)
	public void testSendEmotionMessageWithNumbers() {
		messageService.sendMessage(MessageType.send_emotion, ALPHANUMERIC_PAYLOAD);
	}
	
	@Test(expected = PreconditionFailedException.class)
	public void testSendEmotionMessageTooLong() {
		messageService.sendMessage(MessageType.send_emotion, TOO_LONG_PAYLOAD);
	}
	
	@Test(expected = PreconditionFailedException.class)
	public void testSendEmotionMessageTooShort() {
		messageService.sendMessage(MessageType.send_emotion, TOO_SHORT_PAYLOAD);
	}
}
