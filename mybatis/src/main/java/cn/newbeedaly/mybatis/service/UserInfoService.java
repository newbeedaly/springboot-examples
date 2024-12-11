package cn.newbeedaly.mybatis.service;

import cn.newbeedaly.mybatis.bean.UserInfo;

import java.util.Map;

/**
 * @author newbeedaly
 * @description user_info
 * @date 2023-03-16
 */
public interface UserInfoService {

    /**
     * 新增
     */
    public Boolean insert(UserInfo userInfo);

    /**
     * 删除
     */
    public Boolean delete(int id);

    /**
     * 更新
     */
    public Boolean update(UserInfo userInfo);

    /**
     * 根据主键 id 查询
     */
    public UserInfo load(int id);

    /**
     * 分页查询
     */
    public Map<String, Object> pageList(int offset, int pagesize);

}
