package cn.newbeedaly.springboot.jpa.service;


import cn.newbeedaly.springboot.jpa.common.PageQuery;
import cn.newbeedaly.springboot.jpa.dao.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 保存用户
     */
    void save(User user);

    /**
     * 删除用户
     */
    void deleteById(Long id);

    /**
     * 查询全部用户
     */
    List<User> findAll();

    /**
     * 查询分页数据
     */
    Object findPage(PageQuery pageQuery);

}
