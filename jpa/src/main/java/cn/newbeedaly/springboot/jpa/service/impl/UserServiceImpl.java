package cn.newbeedaly.springboot.jpa.service.impl;

import cn.newbeedaly.springboot.jpa.common.PageQuery;
import cn.newbeedaly.springboot.jpa.dao.UserDao;
import cn.newbeedaly.springboot.jpa.dao.entity.User;
import cn.newbeedaly.springboot.jpa.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Object findPage(PageQuery pageQuery) {
        return userDao.findAll(PageRequest.of(pageQuery.getPage(), pageQuery.getSize()));
    }
}
