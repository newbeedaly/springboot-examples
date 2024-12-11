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
public class ApiResult<T> {

    private String code;
    private String msg;
    private T data;

}
