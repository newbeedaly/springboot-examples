package cn.newbeedaly.shiro.service.impl;

import cn.newbeedaly.shiro.bean.User;
import cn.newbeedaly.shiro.mapper.UserMapper;
import cn.newbeedaly.shiro.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

}
