package cn.newbeedaly.multi.datasource.domain.ds.service.impl;

import cn.newbeedaly.multi.datasource.adaptor.ds.dao.mapper.UserMapper;
import cn.newbeedaly.multi.datasource.adaptor.ds.dao.po.User;
import cn.newbeedaly.multi.datasource.domain.common.config.DataSourceType;
import cn.newbeedaly.multi.datasource.domain.common.config.DynamicDataSourceContextHolder;
import cn.newbeedaly.multi.datasource.domain.ds.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public User getOneDSUser(Integer id) {
        //手动切换数据源
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.DB1.name());
        //return userMapper.selectById(id);
        return userMapper.queryOneDSUser(id);
    }

    public User getTwoDSUser(Integer id) {
        //手动切换数据源
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.DB2.name());
        //return userMapper.selectById(id);
        return userMapper.queryTwoDSUser(id);
    }
}
