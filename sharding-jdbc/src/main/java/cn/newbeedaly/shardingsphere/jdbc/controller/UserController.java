package cn.newbeedaly.shardingsphere.jdbc.controller;

import cn.newbeedaly.shardingsphere.jdbc.dao.po.User;
import cn.newbeedaly.shardingsphere.jdbc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public List<User> getList() {
        return userService.list();
    }

    @PostMapping("/save")
    public void save(@RequestBody User user) {
        userService.save(user);
    }

}
