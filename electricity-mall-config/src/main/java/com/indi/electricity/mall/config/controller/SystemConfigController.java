package com.indi.electricity.mall.config.controller;


import com.indi.electricity.mall.config.entity.SystemConfig;
import com.indi.electricity.mall.config.service.ISystemConfigService;
import com.indi.electricity.mall.config.vo.SystemConfigTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @since 2022-05-16
 */
@RestController
@RequestMapping("/api/config/")
public class SystemConfigController {

    @Autowired
    ISystemConfigService systemConfigService;

    @PostMapping("save")
    public boolean save(@RequestBody SystemConfig systemConfig) {
        return systemConfigService.saveOrUpdate(systemConfig);
    }

    @GetMapping("getTree/{parentId}")
    public List<SystemConfigTree> getTree(@PathVariable(value = "parentId",required = false) Integer parentId) {
        return systemConfigService.getTree(parentId);
    }
}
