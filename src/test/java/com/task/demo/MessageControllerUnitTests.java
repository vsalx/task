package com.task.demo;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.task.demo.controller.ExceptionTranslator;
import com.task.demo.controller.MessageController;
import com.task.demo.enums.MessageType;
import com.task.demo.exception.PreconditionFailedException;
import com.task.demo.repo.MessageRepository;
import com.task.demo.service.MessageService;


//@RunWith(SpringRunner.class)
//@WebMvcTest(MessageController.class)
public class MessageControllerUnitTests {
	
	//@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	MessageController messageController;
	
	 @Mock
	 private MessageService messageService;
	 
	 @MockBean
	 private MessageRepository messageRepository;
	 
	 private final static String WRONG_PATH_VAR = "wrong";
	 private final static String INCORRECT_PAYLOAD = "incorrect_payload";
	 private final static String PAYLOAD = "payload";
	
	 @Before
	 public void init() {
		 MockitoAnnotations.initMocks(this);
		 mockMvc = MockMvcBuilders.standaloneSetup(messageController)
				 .setHandlerExceptionResolvers(createExceptionResolver())
				 .build();
	 }
	 
	 private ExceptionHandlerExceptionResolver createExceptionResolver() {
		    ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
		        protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
		            Method method = new ExceptionHandlerMethodResolver(ExceptionTranslator.class).resolveMethod(exception);
		            return new ServletInvocableHandlerMethod(new ExceptionTranslator(), method);
		        }
		    };
		    exceptionResolver.afterPropertiesSet();
		    return exceptionResolver;
		}
	 
	@Test
	public void testSendTextMessage200() throws Exception {
		mockMvc.perform(post("/messages/" + MessageType.send_text)
				.param("payload", PAYLOAD))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testSendEmotionMessage200() throws Exception {
		mockMvc.perform(post("/messages/" + MessageType.send_emotion)
				.param("payload", PAYLOAD))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testSendMessageWrongPathVariable412() throws Exception {
		mockMvc.perform(post("/messages/" + WRONG_PATH_VAR)
				.param("payload", PAYLOAD))
		.andExpect(status().isPreconditionFailed());
	}
	
	@Test
	public void testSendEmotionMessageWithoutPayloadParam412() throws Exception {
		mockMvc.perform(post("/messages/" + MessageType.send_emotion))
		.andExpect(status().isPreconditionFailed());
	}
	
	@Test
	public void testSendTextMessageWithoutPayloadParam412() throws Exception {
		mockMvc.perform(post("/messages/" + MessageType.send_text))
		.andExpect(status().isPreconditionFailed());
	}
	
	@Test
	public void testWhenPreconditionFailedExceptionThrown412() throws Exception {
		doThrow(PreconditionFailedException.class)
		.when(messageService)
		.sendMessage(MessageType.send_emotion, INCORRECT_PAYLOAD);
		
		mockMvc.perform(post("/messages/" + MessageType.send_emotion)
				.param("payload", INCORRECT_PAYLOAD))
		.andExpect(status().isPreconditionFailed());
	}

}
