package com.returncontinue.consumer;

import org.springframework.stereotype.Component;

import com.returncontinue.dto.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class OrderConsumer {

    @RabbitListener(queues = "order_queue")
    public void consumeMessage(Order order) {
        System.out.println("Message Received from Queue: " + order);
    }
    
}
