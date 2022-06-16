package com.indi.electricity.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import lombok.Data;;

/**
 * <p>
 *
 * </p>
 *
 * @author ph
 * @since 2022-04-25
 */
@Data
@TableName("order")
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("product_id")
    private Integer productId;

    @TableField("pay_amount")
    private BigDecimal payAmount;

    @TableField("add_time")
    private LocalDateTime addTime;

    @TableField("last_update_time")
    private LocalDateTime lastUpdateTime;


}
