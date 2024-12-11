package cn.newbeedaly.easyexcel.excel.common.write.common;

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
public class CommonExportRequest {
    // export service name
    private String exportBean;

    // request params
    private String requestParams;
}
