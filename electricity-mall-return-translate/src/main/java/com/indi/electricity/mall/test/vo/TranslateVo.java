package com.indi.electricity.mall.test.vo;

import com.indi.electricity.mall.annotation.TranslateField;
import com.indi.electricity.mall.annotation.TranslateFields;
import com.indi.electricity.mall.model.config.enums.StateEnum;
import com.indi.electricity.mall.test.entity.Order;
import com.indi.electricity.mall.test.entity.SystemConfig;
import com.indi.electricity.mall.test.service.impl.QueryServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TranslateVo {

    @TranslateFields(/*value = */{
            @TranslateField(searchClass = QueryServiceImpl.class, searchMethod = "findStateList", searchKey = "key", sourceField = "value", targetField = "stateNameByMethod"),
            @TranslateField(searchClass = StateEnum.class, searchKey = "key", sourceField = "value", targetField = "stateNameByEnum")
    })
    //@TranslateField(searchClass = QueryServiceImpl.class, searchMethod = "findStateList", searchKey = "key", sourceField = "value", targetField = "stateNameByMethod")
    //@TranslateField(searchClass = StateEnum.class, searchKey = "key", sourceField = "value", targetField = "stateNameByEnum")
    private Integer state;
    private String stateNameByEnum;
    private String stateNameByMethod;

    @TranslateField(searchClass = SystemConfig.class, searchKey = "parentId", targetField = "children")
    @TranslateField(searchClass = SystemConfig.class, searchKey = "parentId", sourceField = "nameEn", targetField = "childrenName")
    private Integer id;
    private List<SystemConfig> children;
    private List<String> childrenName;

    @TranslateField(searchClass = SystemConfig.class, targetField = "parent")
    private Integer parentId;
    private SystemConfig parent;

    @TranslateField(searchClass = Order.class, targetField = "order", ds = "electricity_mall_1")
    private Integer orderId;
    private Order order;

    public TranslateVo(Integer id, Integer parentId, Integer state, Integer orderId) {
        this.id = id;
        this.parentId = parentId;
        this.state = state;
        this.orderId = orderId;
    }
}
