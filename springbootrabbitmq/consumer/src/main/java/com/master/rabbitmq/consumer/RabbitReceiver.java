package com.master.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Description: springbootrabbitmq消费者
 * Created by masterl on 2020/3/2
 */
@Component
@Log4j
public class RabbitReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "notification", durable = "true"),
            exchange = @Exchange(name = "task-exchange", type = "topic"),
            key = "task.*"
    ))
    @RabbitHandler
    public void onMessage(@Payload Map<String, Object> content,
                          @Headers Map<String, Object> headers,
                          Channel channel) {
        //消费者操作
        log.info("---收到消息，开始消费---");
        log.info(content);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //手动ack
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
