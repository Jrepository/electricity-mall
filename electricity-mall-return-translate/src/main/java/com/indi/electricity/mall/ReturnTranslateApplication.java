package com.indi.electricity.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: admin
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class ReturnTranslateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReturnTranslateApplication.class, args);
    }
}
