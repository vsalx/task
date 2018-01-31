package com.task.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.task.demo.enums.MessageType;

@Entity
@Table(name = "messages")
public class Message implements Serializable {
	
	private static final long serialVersionUID = 5289211684656252069L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message")
	@SequenceGenerator(name = "message", sequenceName = "message_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
    private MessageType type;

	@Column
	private String payload;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	//TODO change to auditable
	private Date createdDate = new Date();
	
	public Message(MessageType type, String payload) {
		setType(type);
		setPayload(payload);
	}

	public Long getId() {
		return id;
	}
	
	public MessageType getType() {
		return type;
	}
	
	public void setType(MessageType type) {
		this.type = type;
	}


	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
}
