package com.indi.electricity.mall.order.service.impl;

import com.indi.electricity.mall.order.entity.Order;
import com.indi.electricity.mall.order.mapper.OrderMapper;
import com.indi.electricity.mall.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ph
 * @since 2022-05-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
}
