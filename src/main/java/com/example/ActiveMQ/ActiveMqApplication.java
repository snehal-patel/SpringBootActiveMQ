package com.example.ActiveMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ActiveMqApplication implements CommandLineRunner{

	@Autowired
	private ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(ActiveMqApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {

        appContext.getBean(Sender.class).send("snehal");

    }

}
