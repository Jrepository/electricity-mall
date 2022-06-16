package com.indi.electricity.mall.feign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FeignTestClientTest {

    @Autowired
    HelloFeignClient helloFeignClient;

    @Test
    void getValueTest() {
        Object result = helloFeignClient.hello();
        System.out.println(result);
    }
}
