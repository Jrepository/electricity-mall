# electricity-mall-eureka-client

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
        <module>generator</module>
        <module>electricity-mall-common</module>
        <module>electricity-mall-order</module>
        <module>electricity-mall-cart</module>
        <module>electricity-mall-second-kill</module>
        <module>electricity-mall-eureka-client</module>
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

