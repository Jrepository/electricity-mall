# electricity-mall-gateway

```yaml
spring:
  cloud:
    gateway:
#        discovery:
#          locator:
#            enabled: true #网关自动映射处理,使用约定的规则，不要使用
#            lower-case-service-id: true #服务名小写转换，eureka默认全部大写
      routes:
        - id: second_kill_rout
          uri: http://localhost:8002
          predicates:
            - Path=/api/**
          filters:
            - StripPrefix=1
#          http://localhost:80/api/api/second/kill/hello/say
#          http://localhost:8002/api/second/kill/hello/say
```
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: second_kill_rout
          uri: http://localhost:8002
          predicates:
            - Path=/api/second/kill/**
#          http://localhost:80/api/second/kill/hello/say
#          http://localhost:8002/api/second/kill/hello/say
```

io.netty.channel.AbstractChannel$AnnotatedNoRouteToHostException: No route to host: edydeMacBook-Pro.local/fe80:0:0:0:aede:48ff:fe00:1122%4:8001
Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException:
Error has been observed at the following site(s):
|_ checkpoint ⇢ org.springframework.cloud.gateway.filter.WeightCalculatorWebFilter [DefaultWebFilterChain]
|_ checkpoint ⇢ HTTP GET "/api/second/kill/hello/say" [ExceptionHandlingWebHandler]
Stack trace:
Caused by: java.net.NoRouteToHostException: No route to host
at sun.nio.ch.Net.connect0(Native Method) ~[na:1.8.0_202]
at sun.nio.ch.Net.connect(Net.java:454) ~[na:1.8.0_202]
at sun.nio.ch.Net.connect(Net.java:446) ~[na:1.8.0_202]
at sun.nio.ch.SocketChannelImpl.connect(SocketChannelImpl.java:648) ~[na:1.8.0_202]




java.lang.RuntimeException: java.net.URISyntaxException: Malformed escape pair at index 37: http://fe80:0:0:0:aede:48ff:fe00:1122%4:8002/api/second/kill/hello/say
