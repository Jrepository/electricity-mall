package com.indi.electricity.mall.config.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.indi.electricity.mall.handler.DateTypeHandler;
import com.indi.electricity.mall.handler.LocalDateTimeHandler;
import com.indi.electricity.mall.model.config.enums.StateEnum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

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

    @ApiModelProperty("中午名称")
    @TableField("name_ch")
    private String nameCh;

    @ApiModelProperty(" 配置项目")
    @TableField("`value`")
    private String value;

    @ApiModelProperty("排序，升序排列  值越小，越靠前")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("0:禁用,1:启用")
    @TableField(value = "state")
    private StateEnum state;

    @ApiModelProperty("创建人")
    @TableField("creator")
    private String creator;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT, typeHandler = DateTypeHandler.class)
    private Date createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE, typeHandler = LocalDateTimeHandler.class)
    private LocalDateTime updateTime;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
