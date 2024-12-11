package cn.newbeedaly.easyexcel.order.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-08-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_order")
public class Order implements Serializable{

    private static final long serialVersionUID=1L;

    /**
    * 主键
    */
    private Long orderNo;
    /**
    * 订单状态
    */
    @TableField("order_status")
    private String orderStatus;
    /**
    * 总价(单位为分)
    */
    @TableField("total_price")
    private Long totalPrice;
    /**
    * 渠道
    */
    @TableField("origin")
    private String origin;
    /**
    * 详细地址
    */
    @TableField("address_detail")
    private String addressDetail;
    /**
    * 订单时间
    */
    @TableField("order_time")
    private Date orderTime;

}
