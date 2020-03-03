package com.master.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by masterl on 2020/3/3
 */
@EnableTransactionManagement
@SpringBootApplication
public class CommonApplication {
    protected final static Logger logger = LoggerFactory.getLogger(CommonApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CommonApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        logger.info("PortalApplication is success!");
    }
}
