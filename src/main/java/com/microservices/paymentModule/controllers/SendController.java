package com.microservices.paymentModule.controllers;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/messaging")
public class SendController {

    @Autowired
    JmsTemplate jmsTemplate;
    @GetMapping("/send/{message}")
    public String send(@PathVariable("message") String message) {
        jmsTemplate.send("demo", new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage object = session.createObjectMessage(message);
                return object;
            }

        });
        return message;
    }
}
