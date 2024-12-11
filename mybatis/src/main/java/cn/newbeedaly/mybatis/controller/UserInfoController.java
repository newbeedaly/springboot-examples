package cn.newbeedaly.mybatis.controller;

import cn.newbeedaly.mybatis.bean.UserInfo;
import cn.newbeedaly.mybatis.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author newbeedaly
 * @description user_info
 * @date 2023-03-16
 */
@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 新增
     *
     * @author newbeedaly
     * @date 2023/03/16
     **/
    @PostMapping("/insert")
    public Boolean insert(@RequestBody UserInfo userInfo) {
        return userInfoService.insert(userInfo);
    }

    /**
     * 刪除
     *
     * @author newbeedaly
     * @date 2023/03/16
     **/
    @PostMapping("/delete")
    public Boolean delete(@RequestBody() UserInfo userInfo) {
        return userInfoService.delete(userInfo.getId());
    }

    /**
     * 更新
     *
     * @author newbeedaly
     * @date 2023/03/16
     **/
    @PostMapping("/update")
    public Boolean update(@RequestBody UserInfo userInfo) {
        return userInfoService.update(userInfo);
    }

    /**
     * 查询 根据主键 id 查询
     *
     * @author newbeedaly
     * @date 2023/03/16
     **/
    @RequestMapping("/load")
    public UserInfo load(int id) {
        return userInfoService.load(id);
    }

    /**
     * 查询 分页查询
     *
     * @author newbeedaly
     * @date 2023/03/16
     **/
    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return userInfoService.pageList(offset, pageSize);
    }

}
