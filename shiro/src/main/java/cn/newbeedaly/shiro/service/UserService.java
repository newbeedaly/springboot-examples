package cn.newbeedaly.shiro.service;

import cn.newbeedaly.shiro.bean.User;

public interface UserService {

    public User findByName(String username);

    public User findById(Integer id);

}
