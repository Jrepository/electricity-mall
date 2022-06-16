package com.indi.electricity.mall.controller;

import com.indi.electricity.mall.model.vo.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/provider", produces = {"application/json;charset=UTF-8"})
//@RequestMapping(value = "/feign/test", produces = {"application/json;charset=UTF-8"})
public class ProviderController {

    @Value("${server.port}")
    String port;

    @GetMapping(value = "/get/str")
    public String getStr() {
        return "xxxxxx";
    }

    @GetMapping(value = "/get/value/{pv}")
    public String getValue(@PathVariable("pv") String pv, @RequestParam(value = "rp", required = false) String rp) {
        return pv + rp;
    }

}

