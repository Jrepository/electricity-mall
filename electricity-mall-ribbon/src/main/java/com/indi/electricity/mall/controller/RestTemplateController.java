package com.indi.electricity.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/rest/template/")
public class RestTemplateController {

    @Autowired
    RestTemplate restTemplate;

    //java.lang.IllegalStateException: No instances available for localhost
    @GetMapping(value = "get/value/v1")
    public String getValueV1() {
        String url = "http://localhost:7005/hello";
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 访问成功
     * @return
     */
    @GetMapping(value = "get/value/v2")
    public String getValueV2() {
        String url = "http://EUREKA-CLIENT/hello";
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 错误的url
     * @return
     */
    @GetMapping(value = "get/value/v3")
    public String getValueV3() {
        String url = "ELECTRICITY-MALL-EUREKA-CLIENT/api/provider/get/str";
        return restTemplate.getForObject(url, String.class);
    }

}
