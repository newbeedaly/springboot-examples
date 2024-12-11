package cn.newbeedaly.springboot.elasticsearch.controller;

import cn.newbeedaly.springboot.elasticsearch.domain.repository.User;
import cn.newbeedaly.springboot.elasticsearch.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/es/user")
public class UserRepositoryController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 保存
     */
    @PostMapping("/save")
    public void save() {
        User user = User.builder().id("1").userName("newbeedaly").mobile("166****8888").build();
        userRepository.save(user);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public void update() {
        Optional<User> userOptional = userRepository.findById("1");
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setUserName("newbeely");
            user.setMobile("166****1111");
            userRepository.save(user);
        }
    }


    /**
     * 通过userName精确查询
     */
    @GetMapping("/findByUserName/{userName}")
    public void findByUserName(@PathVariable("userName") String userName) {
        List<User> result = userRepository.findByUserName(userName);
        result.forEach(res -> log.info("查询结果：{} ", res));
    }

    /**
     * 通过userName模糊查询
     * like支持通配符, 类似startingWith, 如：newbee会自动变为newbee*
     */
    @GetMapping("/findByUserNameLike/{userName}")
    public void findByUserNameLike(@PathVariable("userName") String userName) {
        List<User> result = userRepository.findByUserNameLike(userName);
        result.forEach(res -> log.info("查询结果：{} ", res));
    }

    /**
     * 通过userName模糊查询
     * userName 支持通配符, 如：newbee会自动变为*newbee*
     */
    @GetMapping("/findByUserNameContains/{userName}")
    public void findByUserNameContains(@PathVariable("userName") String userName) {
        List<User> result = userRepository.findByUserNameContains(userName);
        result.forEach(res -> log.info("查询结果：{} ", res));
    }


    /**
     * 高亮显示
     * 使用@Highlight注解
     */
    @GetMapping("/findByMobile/{mobile}")
    public void findByMobile(@PathVariable("mobile") String mobile) {
        List<SearchHit<User>> result = userRepository.findByMobile(mobile);
        result.forEach(res -> log.info("查询结果：{} ", res));
    }

    /**
     * 通过request脚本方式
     */
    @GetMapping("/queryByScript/{userName}")
    public void queryByScript(@PathVariable("userName") String userName) {
        List<User> result = userRepository.queryByScript(userName);
        result.forEach(res -> log.info("查询结果：{} ", res));
    }

}
