## 微服务架构实战02 服务消费者
用于调用服务提供者提供的服务，运行于8010端口
### 微服务配置项
```
spring:
  application:
    name: microservice-consumer-shopping

eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka1:8761/eureka/ ,http://eureka2:8762/eureka/
  instance:
    prefer-ip-address: true
```
