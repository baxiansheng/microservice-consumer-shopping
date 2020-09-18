package ouc.isclab.microservice.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ouc.isclab.microservice.entity.User;

@FeignClient(name = "microservice-provider-user", fallbackFactory = FeignClientFallbackFactory.class)
public interface UserFeignClient {
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    User findById(@PathVariable("id") Long id);
}

@Component
class FeignClientFallback implements UserFeignClient{

    @Override
    public User findById(Long id) {
        User user = new User();
        user.setId(-1L);
        user.setName("Default user");
        return user;
    }
}

@Slf4j
@Component
class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public User findById(Long id) {
                log.error("Fallback reason: ", throwable);
                User user = new User();
                user.setId(-1L);
                user.setName("Default user");
                return user;
            }
        };
    }
}

