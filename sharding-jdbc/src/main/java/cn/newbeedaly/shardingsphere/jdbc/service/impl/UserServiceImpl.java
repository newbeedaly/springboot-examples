package cn.newbeedaly.shardingsphere.jdbc.service.impl;

import cn.newbeedaly.shardingsphere.jdbc.dao.mapper.UserMapper;
import cn.newbeedaly.shardingsphere.jdbc.dao.po.User;
import cn.newbeedaly.shardingsphere.jdbc.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
