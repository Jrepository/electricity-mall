package com.indi.electricity.mall.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.indi.electricity.mall.config.entity.SystemConfig;
import com.indi.electricity.mall.config.vo.SystemConfigTree;
import com.indi.electricity.mall.vo.KeyValueVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ph
 * @since 2022-05-16
 */
public interface ISystemConfigService extends IService<SystemConfig> {

    List<SystemConfigTree> getTree(Integer parentId);

    String tryToGetValue(String nameEn);

    List<String> tryToGetValue(String nameEn,String regex);

    List<KeyValueVo> tryToGetChildren(String nameEn);
}
