package cn.newbeedaly.swagger.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户类
 * <p>
 * create by liqk on 2018/12/12
 */
@Builder
@Data
@ApiModel(description = "用户Model")
public class User {

    @ApiModelProperty(value = "用户ID", name = "id", example = "")
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", name = "username", required = true, example = "liqk")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", name = "password", required = true, example = "123456")
    private String password;

}

