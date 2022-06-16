package com.indi.electricity.mall.log.test.service.impl;


import com.indi.electricity.mall.log.annotation.AfterThrowingLog;
import com.indi.electricity.mall.log.annotation.LogType;
import com.indi.electricity.mall.log.test.service.IAfterThrowingLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AfterThrowingLogServiceImpl implements IAfterThrowingLogService {

    @AfterThrowingLog(type = LogType.PRINT_PARAM)
    @Override
    public void saveList(List<String> dataList,Integer listId) {
//        throw new IllegalArgumentException("save error");
        Assert.isTrue(false,"save error");
    }
}
