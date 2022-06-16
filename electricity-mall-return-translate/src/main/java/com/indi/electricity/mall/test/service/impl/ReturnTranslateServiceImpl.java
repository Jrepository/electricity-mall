package com.indi.electricity.mall.test.service.impl;

import com.indi.electricity.mall.annotation.ReturnTranslate;
import com.indi.electricity.mall.test.service.IReturnTranslateService;
import com.indi.electricity.mall.test.vo.TranslateVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnTranslateServiceImpl implements IReturnTranslateService {

    @Override
    @ReturnTranslate(depth = 2)
    public List<TranslateVo> getTranslateVoList() {
        List<TranslateVo> list = new ArrayList<>();
        list.add(new TranslateVo(12, 0, 1,1));//children=[{id=13}],parent=null
        list.add(new TranslateVo(13, 12, 0,2));//children=[{id=14},{id=15}],parent={id=12}
        list.add(new TranslateVo(15, 13, 1,3));//children=[],parent={id=13}
        return list;
    }
}
