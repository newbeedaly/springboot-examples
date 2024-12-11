package cn.newbeedaly.mybatis.service.impl;

import cn.newbeedaly.mybatis.bean.UserInfo;
import cn.newbeedaly.mybatis.mapper.UserInfoMapper;
import cn.newbeedaly.mybatis.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description user_info
 * @author newbeedaly
 * @date 2023-03-16
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean insert(UserInfo userInfo) {

        // valid
        if (userInfo == null) {
            return Boolean.FALSE;
        }

        userInfoMapper.insert(userInfo);
        return Boolean.TRUE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean delete(int id) {
        userInfoMapper.delete(id);
        return Boolean.TRUE;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean update(UserInfo userInfo) {
        userInfoMapper.update(userInfo);
        return Boolean.TRUE;
    }


    @Override
    public UserInfo load(int id) {
        return userInfoMapper.load(id);
    }


    @Override
    public Map<String,Object> pageList(int offset, int pageSize) {

        List<UserInfo> pageList = userInfoMapper.pageList(offset, pageSize);
        int totalCount = userInfoMapper.pageListCount(offset, pageSize);

        // result
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pageList", pageList);
        result.put("totalCount", totalCount);

        return result;
    }

}
