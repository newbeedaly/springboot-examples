package cn.newbeedaly.multi.datasource.adaptor.ds.dao;

import cn.newbeedaly.multi.datasource.adaptor.ds.dao.mapper.UserMapper;
import cn.newbeedaly.multi.datasource.adaptor.ds.dao.po.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

// 通用service接口 ServiceImpl默认已经实现implements IService<User>
@Repository
public class UserDao extends ServiceImpl<UserMapper, User> {

}
