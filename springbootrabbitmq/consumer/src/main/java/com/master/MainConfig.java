package com.master;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan("com.master.rabbitmq.consumer.*")
public class MainConfig {

}
