package cn.newbeedaly.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description 自动填充处理器
 * @author newbeedaly
 * @date 2023-05-22
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        boolean existCreateTime = metaObject.hasSetter("createTime");
        if(existCreateTime){
            System.out.println("createTime fill");
            setFieldValByName("createTime", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if(updateTime == null){
            System.out.println("updateTime fill");
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}
