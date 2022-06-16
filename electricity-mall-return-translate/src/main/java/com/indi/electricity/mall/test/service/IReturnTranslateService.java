package com.indi.electricity.mall.test.service;


import com.indi.electricity.mall.test.vo.TranslateVo;

import java.util.List;

public interface IReturnTranslateService {

    /**
     * 测试根据枚举、指定方法转译
     * @return
     */
    List<TranslateVo> getTranslateVoList();

}
