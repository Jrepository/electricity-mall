# electricity-mall-config
系统配置参数管理功能

    将一些配置参数通过该模块的功能存于数据库中，可以随时修改
    定义@Config注解，实现类似于@Value的功能，在Bean初始化方法调用后调用BeanPostProcessor的postProcessAfterInitialization方法给Bean中属性赋值

## 示例
**数据准备**
    /electricity-mall/system_config-init.sql

**示例代码**
```java
@SpringBootTest(classes = ConfigApplication.class)
@ExtendWith(SpringExtension.class)
public class RstConfTest {

    @Config(name = "int-val")
    RstConf<Integer> intVal;

    @Config(name = "long-val")
    RstConf<Long> longVal;

    @Config(name = "double-val")
    RstConf<Double> doubleVal;

    @Config(name = "string-val")
    RstConf<String> stringVal;

    @Config(name = "boolean-val")
    RstConf<Boolean> booleanVal;

    @Config(name = "list-val")
    RstConf<List<Integer>> listVal;

    @Config(name = "set-val")
    RstConf<Set<String>> setVal;

    @Config(name = "map-val")
    RstConf<Map<String, String>> mapVal;

    @Config(name = "map-val", findChild = true)
    RstConf<Map<String, String>> children;
}
```
