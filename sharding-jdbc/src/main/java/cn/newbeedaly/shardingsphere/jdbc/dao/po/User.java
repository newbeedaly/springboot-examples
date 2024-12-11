package cn.newbeedaly.shardingsphere.jdbc.dao.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class User {
    private String userId;
    private String userName;
}
