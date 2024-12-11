package cn.newbeedaly.deploy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/deploy")
@RestController
public class DeployController {

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

}
