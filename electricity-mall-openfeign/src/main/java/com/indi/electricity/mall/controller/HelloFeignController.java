package com.indi.electricity.mall.controller;

import com.indi.electricity.mall.feign.HelloFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 *
 */
@RestController
@RequestMapping("/api/feign/")
public class HelloFeignController {

    @Value("${spring.application.name}")
    private String serverName;

    @Value("${server.port}")
    String port;

    @Autowired
    HelloFeignClient helloFeignClient;

    @GetMapping(value = "hello")
    public String getStr() {
        String eurekaClient = helloFeignClient.hello();
        String openfeign = String.format("server name:%s,port:%s", serverName, port);
        return eurekaClient + openfeign;
    }

//    @GetMapping(value = "get/value/{pv}")
//    public String getValue(@PathVariable("pv") String pv,
//                           @RequestParam(value = "rp", required = false) String rp) {
//        return helloFeignClient.getValue(pv, rp);
//    }
}
