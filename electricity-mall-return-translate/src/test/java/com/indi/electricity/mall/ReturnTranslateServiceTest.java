package com.indi.electricity.mall;

import com.google.gson.Gson;
import com.indi.electricity.mall.handler.IQueryHandler;
import com.indi.electricity.mall.model.config.enums.StateEnum;
import com.indi.electricity.mall.test.entity.SystemConfig;
import com.indi.electricity.mall.test.service.IReturnTranslateService;
import com.indi.electricity.mall.test.service.impl.QueryServiceImpl;
import com.indi.electricity.mall.test.vo.TranslateVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest()
public class ReturnTranslateServiceTest {

    @Autowired
    IReturnTranslateService returnTranslateService;

    @Autowired
    IQueryHandler queryHandler;

    @DisplayName("通过制定枚举、指定方法、指定实体类转译")
    @Test
    void getConfigVoListTest() {
        returnTranslateService.getTranslateVoList();
    }



    @Test
    void queryHandlerForMethodTest() {
        queryHandler.queryData(QueryServiceImpl.class, "findStateList", Arrays.asList(1, 0));
    }

    @Test
    void queryHandlerForEnumTest() {
        List list = queryHandler.queryData(StateEnum.class, "key", null, Arrays.asList(1, 0));
        System.out.println(new Gson().toJson(list));
    }

    @Test
    void queryHandlerForEntityTest() {
        List<SystemConfig> list = queryHandler.queryData(SystemConfig.class, "id", null, Arrays.asList(1, 2));
        System.out.println(new Gson().toJson(list));
    }

}
