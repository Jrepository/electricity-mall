# electricity-mall-eureka

### electricity-mall的pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>electricity.mall</groupId>
    <artifactId>electricity-mall</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>

    <modules>
        <module>electricity-mall-eureka</module>
    </modules>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <spring.cloud-version>2021.0.1</spring.cloud-version>
        <cloud-alibaba.version>2.2.6.RELEASE</cloud-alibaba.version>
        <spring.boot-version>2.6.3</spring.boot-version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- cloud全家桶-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud-version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring.boot-version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

### electricity-mall-eureka的pom.xml

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

    <artifactId>electricity-mall-eureka</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
    </dependencies>

</project>
```

### application.yml

```yml
server:
  port: 7001

spring:
  application:
    name: electricity-mall-eureka
#  freemarker:
#    prefer-file-system-access: false

eureka:
  client:
    instance:
      hostname: localhost #指定主机地址
      appname: 注册中心
    fetch-registry: false #指定是否要从注册中心获取服务（注册中心不需要开启）
    register-with-eureka: false #指定是否要注册到注册中心（注册中心不需要开启）
    service-url:
      #单机配置自己，集群陪着其他注册中心的地址
      defaultZone: http://${eureka.client.instance.hostname}:${server.port}/eureka/
#      defaultZone: http://${eureka.client.instance.hostname}:7002/eureka/
  server:
    enable-self-preservation: false #关闭保护模式
```
### 启动类
```java
package com.indi.electricity.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
```

### 启动并访问
> 启动成功后，控制台能看到：`http://localhost:7001/eureka/`
> 
> 访问Eureka：`http://localhost:7001`
