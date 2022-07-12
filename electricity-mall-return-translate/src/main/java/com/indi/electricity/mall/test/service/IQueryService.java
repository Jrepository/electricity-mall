package com.indi.electricity.mall.test.service;


import com.indi.electricity.mall.vo.KeyValueVo;

import java.util.List;

/**
 * @author: admin
 */
public interface IQueryService {


    /**
     * 查询状态并返回key-value
     *
     * @param stateList
     * @return: java.util.List<com.indi.electricity.mall.vo.KeyValueVo>
     **/
    List<KeyValueVo> findStateList(List<Integer> stateList);


}
