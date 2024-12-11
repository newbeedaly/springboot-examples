package cn.newbeedaly.easyexcel.excel.simple.read;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReadOrder {

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
