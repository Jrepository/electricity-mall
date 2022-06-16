package com.indi.electricity.mall.test.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.indi.electricity.mall.annotation.TranslateField;
import com.indi.electricity.mall.handler.DateTypeHandler;
import com.indi.electricity.mall.handler.LocalDateTimeHandler;
import com.indi.electricity.mall.model.config.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ph
 * @since 2022-05-16
 */
@Data
@TableName("system_config")
@ApiModel(value = "SystemConfig对象", description = "")
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键，自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父ID")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty("英文名称")
    @TableField("name_en")
    private String nameEn;

    @TranslateField(searchClass = StateEnum.class, searchKey = "key", sourceField = "value", targetField = "stateName")
    @ApiModelProperty("0:禁用,1:启用")
    @TableField(value = "state")
    private Integer state;
    @TableField(exist = false)
    private String stateName;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT, typeHandler = DateTypeHandler.class)
    private Date createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE, typeHandler = LocalDateTimeHandler.class)
    private LocalDateTime updateTime;

}
