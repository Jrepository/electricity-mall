package com.indi.electricity.mall.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.indi.electricity.mall.order.entity.Order;
import com.indi.electricity.mall.order.service.IOrderService;
import com.indi.electricity.mall.vo.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ph
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/api/order/")
public class OrderController {

    @Autowired
    IOrderService orderService;

    @PostMapping("/save")
    public ResultResponse save(@RequestBody Order order) {
        return ResultResponse.success(orderService.save(order));
    }


    @GetMapping("get/list")
    public ResultResponse<List<Order>> getList() {
        return ResultResponse.success(orderService.list());
    }

    @GetMapping("get/page")
    public ResultResponse<List<Order>> getPage() {
        IPage<Order> page = new Page<>(1,10);
        return ResultResponse.successPage(orderService.page(page));
    }
}
