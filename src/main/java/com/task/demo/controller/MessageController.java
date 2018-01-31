package com.task.demo.controller;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.task.demo.enums.MessageType;
import com.task.demo.exception.PreconditionFailedException;
import com.task.demo.service.MessageService;

@RestController
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@PostMapping
	@RequestMapping("/messages/{type}")
	@ResponseStatus(code = HttpStatus.OK)
	public void sendMessage(@PathVariable(name="type") @NotNull final MessageType type,
			@RequestParam(name = "payload") @NotNull final String payload) throws PreconditionFailedException {
		messageService.sendMessage(type, payload);
	}
}
