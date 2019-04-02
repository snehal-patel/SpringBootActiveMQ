package com.example.ActiveMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@JmsListener(destination = "helloworld.q")
	public void receive(Person message) {
		LOGGER.info("received message='{}'", message);
	}
}