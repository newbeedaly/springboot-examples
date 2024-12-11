package cn.newbeedaly.mybatis.mapper;

import cn.newbeedaly.mybatis.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description user_info
 * @author newbeedaly
 * @date 2023-03-16
 */
@Mapper
@Repository
public interface UserInfoMapper {

    /**
     * 新增
     * @author newbeedaly
     * @date 2023/03/16
     **/
    int insert(UserInfo userInfo);

    /**
     * 刪除
     * @author newbeedaly
     * @date 2023/03/16
     **/
    int delete(int id);

    /**
     * 更新
     * @author newbeedaly
     * @date 2023/03/16
     **/
    int update(UserInfo userInfo);

    /**
     * 查询 根据主键 id 查询
     * @author newbeedaly
     * @date 2023/03/16
     **/
    UserInfo load(int id);

    /**
     * 查询 分页查询
     * @author newbeedaly
     * @date 2023/03/16
     **/
    List<UserInfo> pageList(int offset, int pageSize);

    /**
     * 查询 分页查询 count
     * @author newbeedaly
     * @date 2023/03/16
     **/
    int pageListCount(int offset,int pagesize);

}
