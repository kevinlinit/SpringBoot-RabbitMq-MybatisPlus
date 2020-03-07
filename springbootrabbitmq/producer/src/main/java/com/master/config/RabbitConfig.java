package com.master.config;

import com.master.common.producer.RabbitSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Description: springbootrabbitmq
 * Created by masterl on 2020/3/7
 */
@Configuration
public class RabbitConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    @Lazy(false)
    public RabbitSender rabbitSender() {
        return new RabbitSender(rabbitTemplate);
    }
}
