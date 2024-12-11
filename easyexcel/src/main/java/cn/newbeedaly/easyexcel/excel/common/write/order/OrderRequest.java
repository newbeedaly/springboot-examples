package cn.newbeedaly.easyexcel.excel.common.write.order;

import cn.newbeedaly.easyexcel.excel.common.write.common.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by newbeedaly
 * since 2021-08-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest extends BaseRequest {

    private String orderNo;

}
