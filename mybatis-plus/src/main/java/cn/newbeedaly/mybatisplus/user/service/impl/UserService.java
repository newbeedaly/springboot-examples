package cn.newbeedaly.mybatisplus.user.service.impl;

import cn.newbeedaly.mybatisplus.user.dao.mapper.UserMapper;
import cn.newbeedaly.mybatisplus.user.dao.po.User;
import cn.newbeedaly.mybatisplus.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author newbeedaly
 * @since 2021-06-03
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}
