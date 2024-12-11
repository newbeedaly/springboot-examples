package cn.newbeedaly.shardingsphere.jdbc.dao.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_address")
public class Address {
    private String id;
    private String code;
    private String name;
}
