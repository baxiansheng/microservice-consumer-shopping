package ouc.isclab.microservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ouc.isclab.microservice.entity.User;
import ouc.isclab.microservice.feign.UserFeignClient;

import java.util.List;


/**
 * @ Classname  ：ShoppingController
 * @ Description：消费者购物控制器
 * @ Date       ：Created in 2020/4/3 14:50
 * @ Author     ：vimer
 */
@Slf4j
@RestController
public class ShoppingController {

    @Autowired
     private RestTemplate restTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

//    @HystrixCommand(fallbackMethod = "findUserByIdFallback")
//    @GetMapping("/user/{id}")
//    public User findUserById(@PathVariable Long id){
//        return restTemplate.getForObject("http://microservice-provider-user/user/"+id,User.class);
//    }
//
//    public User findUserByIdFallback(Long id, Throwable throwable){
//        log.error("Fallback reason:", throwable);
//        User user = new User();
//        user.setId(-1L);
//        user.setName("Default user");
//        return user;
//    }

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable Long id){
        return userFeignClient.findById(id);
    }

    @GetMapping("/users")
    public List<User> findAllUsers(){
        return restTemplate.getForObject("http://microservice-provider-user/users", List.class);
    }

    @GetMapping("user-service-instance-log")
    public void logUserServiceInstance(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("microservice-provider-user");
        log.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
    }
}
