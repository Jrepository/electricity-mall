package com.indi.electricity.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/second/kill/hello/")
public class HelloController {

    @GetMapping("say")
    public String say() {
        return "Hi,second-kill...";
    }
}
