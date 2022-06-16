package com.indi.electricity.mall.order.controller;


import com.indi.electricity.mall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ph
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    public Object list() {
        return orderService.list();
    }
}
