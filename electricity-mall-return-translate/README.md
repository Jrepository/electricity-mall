# electricity-mall-return-translate
字段转译功能

    将某个字段的数据转译成其它数据，并将转译后的数据存到其它字段中。
    分类：根据枚举转译、根据指定方法转译、根据数据库中的数据转译
     
    转译功能采用反射实现，将耗费一定时间
    转译失败时，转译后的数据为空，其它数据正常返回，即无论什么异常，哪个转译字段引起的异常，都不会影响原返回正常返回

## 注解说明
    
    @ReturnTranslate：使用在方法上，使用该注解的方法需要将返回结果进行转译
    @TranslateFields：使用在字段上，使用该注解的字段需要将该字段的数据转译
    @TranslateField： 使用在字段上，设置字段转译的相关属性

@TranslateFields与@TranslateField使用方式参考@Results与@Result
```
    @Results(value = {
        @Result(property = "created", column = "created_date", typeHandler = LocalDateTimeTypeHandler.class, jdbcType = JdbcType.TIMESTAMP),
        @Result(property = "updated", column = "updated_date", typeHandler = LocalDateTimeTypeHandler.class, jdbcType = JdbcType.TIMESTAMP)
    })
```

## **列子**

**数据准备**

    根据枚举转译
        StateEnum 0：禁用，1：启用

    根据指定方法转译
        方法名：findStateList
        方法所在的类：QueryServiceImpl
        返回值：List<KeyValueVo> list = [{0,"禁用},{1,"启用"}];

    根据数据库中的数据转译
        表：/electricity-mall/system_config-init.sql
        实体类：SystemConfig
        表中的数据：
            id   parent_id ...
            12      0      ...
            13      12     ...
            14      13     ...
            15      13     ...

**示例代码**

```java
@Service
public class ReturnTranslateServiceImpl implements IReturnTranslateService {

    @Override
    @ReturnTranslate(depth = 2)
    public List<TranslateVo> getTranslateVoList() {
        List<TranslateVo> list = new ArrayList<>();
        list.add(new TranslateVo(12, 0, 1));//children=[{id=13}],parent=null
        list.add(new TranslateVo(13, 12, 0));//children=[{id=14},{id=15}],parent={id=12}
        list.add(new TranslateVo(15, 13, 1));//children=[],parent={id=13}
        return list;
    }
}
```

```java
/**
 * 注释的代码都是可用的
 */
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
    private Integer id;
    private List<SystemConfig> children;

    @TranslateField(searchClass = SystemConfig.class, targetField = "parent")
    private Integer parentId;
    private SystemConfig parent;

    public TranslateVo(Integer id,Integer parentId, Integer state) {
        this.id = id;
        this.parentId = parentId;
        this.state = state;
    }
}
```

**结果分析**

    转译前的数据
            id  parent_id   status
            12      0         1
            13      12        0             
            15      13        1

    转译前的数据
            id  parent_id   status  stateNameByEnum    stateNameByMethod       children                     parent
            12      0         1             启用              启用           [{id=13,...}]                     
            13      12        0             禁用              禁用           [{id=14,...},{id=15,...}]       {id=12,...} 
            15      13        1             启用              启用                                           {id=13,...} 

    
