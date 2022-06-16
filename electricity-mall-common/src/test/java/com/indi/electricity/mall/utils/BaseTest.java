package com.indi.electricity.mall.utils;


import lombok.extern.log4j.Log4j2;

@Log4j2
public class BaseTest {


    //    public void output(Object object) throws JsonProcessingException {
    public void output(Object object) {
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        log.info("\n\n\n> {} output: {} \n\n\n ", method, object);
//        String result = (object instanceof String) ? (String) object : new ObjectMapper().writeValueAsString(object);
//        log.info("\n\n\n> {} output: {} \n\n\n ", method, result);
    }
}
