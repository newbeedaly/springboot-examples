package cn.newbeedaly.swagger.controller;

import cn.newbeedaly.swagger.bean.User;
import cn.newbeedaly.swagger.common.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Hello world!
 */
@Api(value = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "新增用户", notes = "新增注册")
    @PostMapping(value = "/")
    public ApiResult<String> create(@Valid @RequestBody User user) {
        logger.info("create:::{}", user.toString());
        return new ApiResult<>("0000", "新增成功.", "");
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PutMapping(value = "/")
    public ApiResult<String> update(@Valid @RequestBody User user) {
        logger.info("create:::{}", user.toString());
        return new ApiResult<>("0000", "修改成功.", "");
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
//  swagger 2.8.2 此注解有bug
//  @ApiImplicitParams({
//      @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "path", dataType = "java.lang.Integer")
//  })
    @DeleteMapping(value = "/{userId}")
    public ApiResult<String> deleteByUserId(@PathVariable("userId") Integer userId) {
        logger.info("delete:::{}", userId);
        return new ApiResult<>("0000", "删除成功.", "");
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
//  @ApiImplicitParams({
//      @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "", dataType = "java.lang.Integer")
//  })
    @GetMapping(value = "/{userId}")
    public ApiResult<User> getByUserId(@PathVariable("userId") Integer userId) {
        logger.info("query:::{}", userId);
        return new ApiResult<>("0000", "查询成功.", User.builder().id(userId).username("newbeedaly").build());
    }

}