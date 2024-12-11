package cn.newbeedaly.mybatisplus.user.dao;

import cn.newbeedaly.mybatisplus.user.dao.mapper.UserMapper;
import cn.newbeedaly.mybatisplus.user.dao.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  DAO实现类
 * </p>
 *
 * @author newbeedaly
 * @since 2021-06-03
 */
@Component
public class UserDao extends ServiceImpl<UserMapper, User> implements IService<User> {

}
