package com.indi.electricity.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "KeyValueVo对象", description = "键值对")
public class KeyValueVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "键")
    private Object key;

    @ApiModelProperty(value = "值")
    private Object value;
}
