package cn.newbeedaly.easyexcel.excel.common.write.common;

/**
 * create by newbeedaly
 * since 2021-08-24
 */
public interface IDataExportService {

    ApiResult<String> exportFile(CommonExportRequest request);

}
