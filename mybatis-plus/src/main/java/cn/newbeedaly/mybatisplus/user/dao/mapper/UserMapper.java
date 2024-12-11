package cn.newbeedaly.mybatisplus.user.dao.mapper;

import cn.newbeedaly.mybatisplus.user.dao.po.User;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author newbeedaly
 * @since 2021-06-03
 */
public interface UserMapper extends BaseMapper<User> {

    @SqlParser(filter = true)
        //@Select("select * from mysql_data ${ew.customSqlSegment}")
    List<User> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);

    IPage<User> getUserPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
