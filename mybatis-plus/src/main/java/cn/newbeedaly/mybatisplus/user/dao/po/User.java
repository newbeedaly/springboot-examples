package cn.newbeedaly.mybatisplus.user.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author newbeedaly
 * @since 2021-06-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("mp_user")
public class User implements Serializable{

    private static final long serialVersionUID=1L;

    /**
    * 自增主键
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user_name")
    private String userName;
    @TableField("age")
    private Integer age;
    @TableField("mobile_number")
    private String mobileNumber;
    @TableField("create_time")
    private Date createTime;

}
