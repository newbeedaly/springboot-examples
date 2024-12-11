package cn.newbeedaly.easyexcel.excel.common.write.common;

public interface IExportService<Q extends BaseRequest> {

    /**
     * 返回用于接收参数的Class
     *
     * @return Q.class
     */
    Class<Q> paramClass();

    /**
     * 导出类型
     * @return 导出类型描述
     */
    String title();

    /**
     * 对导出请求条件的中文翻译，如：订单状态：正常，出库状态：待出库，下单时间：2020-01-01—2020-03-31
     * @param request 请求条件
     * @return 导出条件翻译
     */
    String conditions(Q request);

    /**
     * 根据导出条件，生成文件，上传到OSS
     * @param request 请求条件
     * @return OSS文件相对路径
     */
    String makefile(Q request);

}
