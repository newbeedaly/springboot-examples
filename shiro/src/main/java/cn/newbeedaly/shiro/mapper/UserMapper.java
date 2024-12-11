package cn.newbeedaly.shiro.mapper;

import cn.newbeedaly.shiro.bean.User;

public interface UserMapper {

    User findByName(String username);

    User findById(Integer id);

}
