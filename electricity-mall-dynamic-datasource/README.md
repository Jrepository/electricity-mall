# electricity-mall-dynamic-datasource

**项目描述：**

    多数据源学习

**Step1：实现数据源切换(dynamic-datasource-spring-boot-starter)**

> ⚠️ 这一步就能实现数据源切换

 ```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>electricity-mall</artifactId>
        <groupId>electricity.mall</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>electricity-mall-dynamic-datasource</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- 与多数据源无关，实体类一些注解所需要的依赖 -->
        <dependency>
            <groupId>electricity.mall</groupId>
            <artifactId>electricity-mall-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.3.4</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version>2.5.3</version>
        </dependency>
    </dependencies>
</project>
```

```yaml
server:
  port: 8000


spring:
  #   单数据源配置
  #  datasource:
  #    url: jdbc:mysql://127.0.0.1:3306/seata_order?useSSL=false&useUnicode=true&characterEncoding=UTF-8
  #    driver-class-name: com.mysql.jdbc.Driver
  #    username: root
  #    password: 123456

  autoconfigure:
    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
  datasource:
    #    type: com.alibaba.druid.pool.DruidDataSource
    #    druid:
    #      filters: stat,log4j
    #      initial-size: 30
    #      max-active: 150
    #      min-idle: 5
    #      max-wait: 10000
    #多数据源配置
    dynamic:
      # 设置默认的数据源或者数据源组，默认值即为 master
      primary: order-ds
      datasource:
        #数据库
        order-ds:
          url: jdbc:mysql://127.0.0.1:3306/seata_order?useSSL=false&useUnicode=true&characterEncoding=UTF-8
          driver-class-name: com.mysql.jdbc.Driver
          username: root
          password: 123456
        #数据库
        product-ds:
          url: jdbc:mysql://127.0.0.1:3306/seata_storage?useSSL=false&useUnicode=true&characterEncoding=UTF-8
          driver-class-name: com.mysql.jdbc.Driver
          username: root
          password: 123456
```

**Step2：加入(druid-spring-boot-starter)**

pom.xml

 ```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>electricity-mall</artifactId>
        <groupId>electricity.mall</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>electricity-mall-dynamic-datasource</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- 与多数据源无关，实体类一些注解所需要的依赖 -->
        <dependency>
            <groupId>electricity.mall</groupId>
            <artifactId>electricity-mall-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.21</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.3.4</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version>2.5.3</version>
        </dependency>
    </dependencies>
</project>
```

⚠️ Step2与Step1区别
> pom.xml：druid-spring-boot-starter
>
> yml：spring:autoconfigure:exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
>
> 启动类为：@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
>
> 其中yml配置与启动类中的exclude = {DruidDataSourceAutoConfigure.class}二选一即可

## 问题

    Step2过程中，启动项目报错,错误内容(step2-error.png)，报错原因应该是druid-spring-boot-starter的版本有问题（1.2.8），修改为1.1.10即可.
    该错误有其它解决方案后续再补充，或者等1.2.8不再报错时，再使用1.2.8版本

    版本问题：druid-spring-boot-starter（1.1.0）错误信息如下，解决方案修改版本信息1.1.14
    org.springframework.beans.factory.BeanDefinitionStoreException: Failed to process import candidates for configuration class [com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration]; nested exception is java.io.FileNotFoundException: class path resource [com/alibaba/druid/spring/boot/autoconfigure/stat/DruidSpringAopConfiguration.class] cannot be opened because it does not exist
	at org.springframework.context.annotation.ConfigurationClassParser.processImports(ConfigurationClassParser.java:610) ~[spring-context-5.3.15.jar:5.3.15]


    版本问题：druid-spring-boot-starter（1.1.14）错误信息如下，解决方案修改版本信息1.1.21
    Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.dao.InvalidDataAccessApiUsageException: Error attempting to get column 'add_time' from result set.  Cause: java.sql.SQLFeatureNotSupportedException
    ; null; nested exception is java.sql.SQLFeatureNotSupportedException] with root cause
