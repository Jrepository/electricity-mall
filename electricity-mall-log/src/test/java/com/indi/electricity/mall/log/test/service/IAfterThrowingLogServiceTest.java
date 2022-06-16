package com.indi.electricity.mall.log.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class IAfterThrowingLogServiceTest {

    @Autowired
    IAfterThrowingLogService afterThrowingLogService;

    @Test
    void saveListTest(){
        afterThrowingLogService.saveList(Arrays.asList("1","2"),10);
    }
}
