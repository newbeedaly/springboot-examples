package cn.newbeedaly.mybatis.sqlparse;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper<T>{

    @Select("select * from user where id = #{id} and name = #{name}")
    List<T> selectUserList(Integer id, String name);

}
