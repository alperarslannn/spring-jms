package com.alper.arslan.springjms.listener;

import com.alper.arslan.springjms.config.JmsConfig;
import com.alper.arslan.springjms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class HelloMessageListener {
    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listener(@Payload HelloWorldMessage helloWorldMessage,
                         @Headers MessageHeaders headers, Message message) {
        System.out.println("I got a message!!!!");
        System.out.println(helloWorldMessage);

    }
}
