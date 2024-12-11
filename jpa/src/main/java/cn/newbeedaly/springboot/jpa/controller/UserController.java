package cn.newbeedaly.springboot.jpa.controller;

import cn.newbeedaly.springboot.jpa.common.PageQuery;
import cn.newbeedaly.springboot.jpa.dao.entity.User;
import cn.newbeedaly.springboot.jpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save")
    public Object save(@RequestBody User user) {
        userService.save(user);
        return 1;
    }

    @PostMapping(value = "/delete")
    public Object deleteById(Long id) {
        userService.deleteById(id);
        return 1;
    }

    @GetMapping(value = "/findAll")
    public Object findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/findPage")
    public Object findPage(PageQuery pageQuery) {
        return userService.findPage(pageQuery);
    }

}
