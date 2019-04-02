package com.example.ActiveMQ;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Sender {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(String message) {

		Person p = new Person();
		p.setName(message);

		LOGGER.info("sending message='{}'", p);
		jmsTemplate.convertAndSend("helloworld.q", p);
	}
}

class Person {
	/**
	 * 
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}

@Component
class PersonMessageConvertor implements MessageConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonMessageConvertor.class);

	ObjectMapper mapper;

	public PersonMessageConvertor() {
	    mapper = new ObjectMapper();
	  }

	@Override
	public Message toMessage(Object object, Session session) throws JMSException {
		Person person = (Person) object;
		String payload = null;
		try {
			payload = mapper.writeValueAsString(person);
			LOGGER.info("outbound json='{}'", payload);
		} catch (JsonProcessingException e) {
			LOGGER.error("error converting form person", e);
		}

		TextMessage message = session.createTextMessage();
		message.setText(payload);

		return message;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException {
		TextMessage textMessage = (TextMessage) message;
		String payload = textMessage.getText();
		LOGGER.info("inbound json='{}'", payload);

		Person person = null;
		try {
			person = mapper.readValue(payload, Person.class);
		} catch (Exception e) {
			LOGGER.error("error converting to person", e);
		}

		return person;
	}
}