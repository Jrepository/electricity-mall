package com.indi.electricity.mall.test.service;


import com.indi.electricity.mall.vo.KeyValueVo;

import java.util.List;

public interface IQueryService {

    List<KeyValueVo> findStateList(List<Integer> stateList);


}
