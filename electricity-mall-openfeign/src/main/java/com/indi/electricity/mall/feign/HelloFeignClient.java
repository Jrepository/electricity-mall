package com.indi.electricity.mall.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *@FeignClient(value = "http://localhost:7003/api/provider")
 * @FeignClient(name = "helloFeign", url = "http://localhost:7003/api/provider")
 * @FeignClient(name = "helloFeign", url = "http://EUREKA-CLIENT/api/provider")//class UnknownHostException extends IOException {
 * @FeignClient(name = "workflow", url = "${visit.halo.permission.url}", configuration = FeginClientConfig.class)
 */
@FeignClient(value = "EUREKA-CLIENT",path = "/api/eureka/client/")
public interface HelloFeignClient {

    @GetMapping(value = "hello")
    String hello();

}

