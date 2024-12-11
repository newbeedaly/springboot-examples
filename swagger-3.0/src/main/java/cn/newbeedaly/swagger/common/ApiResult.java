package cn.newbeedaly.swagger.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "响应学生对象")
public class ApiResult<T> {

    private static final String SUCCESS_CODE = "0000";
    private static final String SUCCESS_MESSAGE = "成功";

    @ApiModelProperty(value = "响应码", name = "code", required = true, example = SUCCESS_CODE)
    private String code;
    @ApiModelProperty(value = "响应消息", name = "msg", required = true, example = SUCCESS_MESSAGE)
    private String msg;
    @ApiModelProperty(value = "响应对象", name = "data")
    private T data;

}
