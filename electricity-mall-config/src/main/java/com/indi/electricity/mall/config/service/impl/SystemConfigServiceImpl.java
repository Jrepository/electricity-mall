package com.indi.electricity.mall.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.indi.electricity.mall.config.entity.SystemConfig;
import com.indi.electricity.mall.config.mapper.SystemConfigMapper;
import com.indi.electricity.mall.config.service.ISystemConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.indi.electricity.mall.config.vo.SystemConfigTree;
import com.indi.electricity.mall.utils.RegexUtil;
import com.indi.electricity.mall.vo.KeyValueVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ph
 * @since 2022-05-16
 */
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements ISystemConfigService {

    @Override
    public List<SystemConfigTree> getTree(Integer parentId) {
        List<SystemConfig> configList = this.allList();
        if (CollectionUtils.isEmpty(configList)) {
            return Collections.emptyList();
        }
        List<SystemConfig> root = configList.stream().filter(item -> parentId == item.getParentId()).collect(Collectors.toList());
        Map<Integer, List<SystemConfig>> childrenMap = configList.stream().filter(item -> parentId != item.getParentId()).collect(Collectors.groupingBy(SystemConfig::getParentId));
        return getTree(root, childrenMap);
    }

    private List<SystemConfigTree> getTree(List<SystemConfig> rootList, Map<Integer, List<SystemConfig>> childrenMap) {
        if (CollectionUtils.isEmpty(rootList)) {
            return Collections.emptyList();
        }
        List<SystemConfigTree> treeList = rootList.stream().map(config -> {
            SystemConfigTree configTree = new SystemConfigTree();
            BeanUtils.copyProperties(config, configTree);
            if (childrenMap != null && childrenMap.containsKey(config.getId())) {
                configTree.setChildren(getTree(childrenMap.get(config.getId()), childrenMap));
            }
            return configTree;
        }).collect(Collectors.toList());
        return treeList;
    }


    private List<SystemConfig> allList() {
        return getConfigList(null);
    }


    @Override
    public String tryToGetValue(String nameEn) {
        return getByNameEn(nameEn).getValue();
    }

    @Override
    public List<String> tryToGetValue(String nameEn, String regex) {
        SystemConfig systemConfig = getByNameEn(nameEn);
        if (systemConfig == null || StringUtils.isEmpty(systemConfig.getValue())) {
            return Collections.emptyList();
        }
        List<String> valueList = Arrays.asList(RegexUtil.split(systemConfig.getValue(), regex));
        return valueList.stream().map(String::trim).collect(Collectors.toList());
    }

    @Override
    public List<KeyValueVo> tryToGetChildren(String nameEn) {
        return getChildren(getByNameEn(nameEn).getId());
    }

    private List<KeyValueVo> getChildren(Integer parentId) {
        List<SystemConfig> configEntityList = getConfigList(parentId);
        return configEntityList.stream().map(config -> {
            return new KeyValueVo(config.getNameEn(), config.getValue());
        }).collect(Collectors.toList());
    }

    private List<SystemConfig> getConfigList(Integer parentId) {
        LambdaQueryWrapper<SystemConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SystemConfig::getId, SystemConfig::getNameEn, SystemConfig::getNameCh,
                        SystemConfig::getValue, SystemConfig::getParentId, SystemConfig::getState,
                        SystemConfig::getCreateTime, SystemConfig::getUpdateTime)
                .eq(null != parentId, SystemConfig::getParentId, parentId)
                .orderBy(true, true, SystemConfig::getSort);
        return this.list(queryWrapper);
    }


    private SystemConfig getByNameEn(String nameEn) {
        LambdaQueryWrapper<SystemConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConfig::getNameEn, nameEn);
        SystemConfig configEntity = this.getOne(queryWrapper);
        return configEntity;
    }
}
