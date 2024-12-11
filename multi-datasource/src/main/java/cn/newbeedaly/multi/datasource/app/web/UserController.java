package cn.newbeedaly.multi.datasource.app.web;

import cn.newbeedaly.multi.datasource.adaptor.ds.dao.po.User;
import cn.newbeedaly.multi.datasource.domain.ds.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/one/{id}")
    public User getOneDSUser(@PathVariable("id") Integer id){
       return userService.getOneDSUser(id);
    }

    @GetMapping("/two/{id}")
    public User getTwoDSUser(@PathVariable("id") Integer id){
        return userService.getTwoDSUser(id);
    }

}
