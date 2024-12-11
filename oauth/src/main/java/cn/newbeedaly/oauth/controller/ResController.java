package cn.newbeedaly.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源
 *
 * post localhost:8020/oauth/token?client_id=newbeedaly&client_secret=123456&grant_type=client_credentials
 * get localhost:8020/res/1 Header Authorization -> Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzIl0sInNjb3BlIjpbInF1ZXJ5Il0sImV4cCI6MTY4NTY1NjI4NywianRpIjoiZjQ5YjExYmQtMDkwOC00MTc0LTk1MWItNjJhZTI4NjdmOTM3IiwiY2xpZW50X2lkIjoibmV3YmVlZGFseSJ9.SkWTmaB7fpubUke2xZDmMFaDp8sGJ754zXNeVVn8_Rs
 */
@RequestMapping("/res")
@RestController
public class ResController {

    /**
     * Nginx网关回调验证接口
     */
    @GetMapping("/verify")
    public String verify() {
        return "success";
    }

    @GetMapping("/{id}")
    public String getResById(@PathVariable("id") String id){
        return "get the resource " + id;
    }

}
