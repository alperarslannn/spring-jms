package com.alper.arslan.springjms.sender;

import com.alper.arslan.springjms.config.JmsConfig;
import com.alper.arslan.springjms.model.HelloWorldMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        //System.out.println("I'm sending message");
        HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello world!").build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
        //System.out.println("Message sent.");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        System.out.println("I'm sending message");

        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder().id(UUID.randomUUID()).message("Hello").build();

        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(mapper.writeValueAsString(helloWorldMessage));
                    helloMessage.setStringProperty("_type","com.alper.arslan.springjms.model.HelloWorldMessage");
                    System.out.println("Sending hello");
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("boom");
                }

            }
        });

        System.out.println(receivedMessage.getBody(String.class));
    }
}
