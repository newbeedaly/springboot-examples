package cn.newbeedaly.shiro.bean;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private String perms;

}
