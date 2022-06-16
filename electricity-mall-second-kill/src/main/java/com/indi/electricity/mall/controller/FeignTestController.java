package com.indi.electricity.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign/test/")
//@RequestMapping(value = "/feign/test", produces = {"application/json;charset=UTF-8"})
public class FeignTestController {

    @GetMapping(value = "get/value/{pv}")
    public String getValue(@PathVariable("pv") String pv, @RequestParam("rp") String rp) {
        return pv + rp;
    }
}
