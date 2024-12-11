package cn.newbeedaly.multi.datasource.adaptor.ds.dao.mapper;

import cn.newbeedaly.multi.datasource.adaptor.ds.dao.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where user_id=#{id}")
    User queryTwoDSUser(Integer id);

    @Select("select * from user where user_id=#{id}")
    User queryOneDSUser(Integer id);
}
