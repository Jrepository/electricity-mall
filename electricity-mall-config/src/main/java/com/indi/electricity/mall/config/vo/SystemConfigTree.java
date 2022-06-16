package com.indi.electricity.mall.config.vo;

import com.indi.electricity.mall.config.entity.SystemConfig;
import lombok.Data;

import java.util.List;

@Data
public class SystemConfigTree extends SystemConfig {

    List<SystemConfigTree> children;
}
