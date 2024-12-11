package cn.newbeedaly.easyexcel.excel.common.read.order;

import cn.newbeedaly.easyexcel.excel.common.read.common.BaseReadCommon;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.*;

import java.util.Date;

/**
 * @author newbeedaly
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReadOrderCommon extends BaseReadCommon {

    @ExcelProperty("订单编号")
    private Long orderNo;
    @ExcelProperty("订单状态")
    private String orderStatus;
    @ExcelProperty("总价(单位为分)")
    private Long totalPrice;
    @ExcelProperty("渠道")
    private String origin;
    @ExcelProperty("详细地址")
    private String addressDetail;
    @ExcelProperty("订单时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

}
