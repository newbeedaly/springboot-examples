package cn.newbeedaly.easyexcel.excel.common.write.order;

import cn.newbeedaly.easyexcel.excel.common.write.common.AbstractExportService;
import cn.newbeedaly.easyexcel.excel.simple.write.WriteOrder;

import java.util.LinkedList;
import java.util.List;

public class OrderExportService extends AbstractExportService<OrderRequest> {

    @Override
    public Class<OrderRequest> paramClass() {
        return OrderRequest.class;
    }

    @Override
    public String title() {
        return "订单表";
    }

    @Override
    public String conditions(OrderRequest request) {
        return "请求条件:" + request.getOrderNo();
    }

    @Override
    public String makefile(OrderRequest request) {
        // 分页查询得到结果
        List<WriteOrder> list = new LinkedList<>();
        return super.writeExcel(WriteOrder.class, list);
    }

}
