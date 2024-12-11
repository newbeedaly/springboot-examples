package cn.newbeedaly.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world";
    }

    @RequestMapping("/index")
    public String testThymeleaf(Model model) {
        model.addAttribute("name", "newbeedaly");
        return "index";
    }


    @RequestMapping("/add")
    public String add() {
        return "user/add";
    }


    @RequestMapping("/update")
    public String update() {
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/noAuth")
    public String noAuth() {
        return "noAuth";
    }


    @RequestMapping("/login")
    public String login(String username, String password, Model model) {

        // 编写shiro让你在操作

        // 1. 获取subject
        Subject subject = SecurityUtils.getSubject();

        // 2. 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        // 3. 执行登陆操作
        try {
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) {
            // 登陆失败；用户名不存在
            model.addAttribute("msg", "用户不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }

    }


}
