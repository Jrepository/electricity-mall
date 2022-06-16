package com.indi.electricity.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.indi.electricity.mall.order.entity.Order;
import com.indi.electricity.mall.order.mapper.OrderMapper;
import com.indi.electricity.mall.order.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ph
 * @since 2022-04-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
