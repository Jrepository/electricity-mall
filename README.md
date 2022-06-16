# electricity-mall
项目学习与积累

    通用模块（与项目无关，任何项目中都可以直接拿来使用的功能）
        electricity-mall-common
        electricity-mall-config
        electricity-mall-log
        electricity-mall-return-translate
        generator

## electricity-mall-cart

## electricity-mall-common
工具类、通用方法

## electricity-mall-config
系统配置参数管理功能

    将一些配置参数通过该模块的功能存于数据库中，可以随时修改
    定义@Config注解，实现类似于@Value的功能，在Bean初始化方法调用后调用BeanPostProcessor的postProcessAfterInitialization方法给Bean中属性赋值
    具体使用参考com.indi.electricity.mail.RstConfTest类
## electricity-mall-dynamic-datasource
（测试）多数据源
## electricity-mall-eureka-client
（测试）spring cloud eureka client
## electricity-mall-eureka-server
spring cloud eureka server
## electricity-mall-gateway 
（测试）spring cloud gateway
## electricity-mall-openfeign
（测试）spring cloud openfeign
## electricity-mall-order

## electricity-mall-return-translate
字段转译功能

    将某个字段的数据转译成其它数据，并将转译后的数据存到其它字段中。
    分类：根据枚举转译、根据指定方法转译、根据数据库中的数据转译
    具体使用参考com.indi.electricity.mall.test包下的代码


    知识点：
        反射：ReflectionUtils
        多数据源：DynamicDataSourceContextHolder中的peek、push、clear方法
        自定义注解：
            元注解
                @Documented
                @Retention(RetentionPolicy.RUNTIME)
                @Target(ElementType.FIELD)
                @Repeatable(TranslateFields.class)
                    jdk8中新增的注解，使用该元注解的注解是可重复的，即可以在同一个地方使用，没有使用该元注解的注解，同一个地方使用会报错
        

## electricity-mall-ribbon
（测试）spring cloud ribbon
## electricity-mall-second-kill

## electricity-mall-sharding-jdbc
（测试）分库分表

    sharding-jdbc实现mysql的分库分表功能
## generator
代码生成器
