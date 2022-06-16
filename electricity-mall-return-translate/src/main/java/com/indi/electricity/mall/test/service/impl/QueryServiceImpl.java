package com.indi.electricity.mall.test.service.impl;


import com.indi.electricity.mall.test.service.IQueryService;
import com.indi.electricity.mall.vo.KeyValueVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QueryServiceImpl implements IQueryService {


    @Override
    public List<KeyValueVo> findStateList(List<Integer> stateList) {
        Map<Integer, String> keyValueVoMap = getKeyValueVoMap();

        List<KeyValueVo> rst = stateList.stream().map(state -> {
            return new KeyValueVo(state, keyValueVoMap.get(state));
        }).collect(Collectors.toList());
        return rst;
    }


    private Map<Integer, String> getKeyValueVoMap() {
        Map<Integer, String> map = new HashMap<Integer, String>() {{
            put(0, "禁用");
            put(1, "启用");
            put(3, "其他");
        }};
        return map;
    }
}
