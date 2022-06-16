package com.indi.electricity.mall.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eureka/client/")
public class HelloEurekaController {

    @Value("${server.port}")
    String port;

    @GetMapping(value = "/hello")
    public String hello() {
        return "hello eureka" + port;
    }


}

