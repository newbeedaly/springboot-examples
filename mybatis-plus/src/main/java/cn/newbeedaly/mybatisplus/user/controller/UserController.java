package cn.newbeedaly.mybatisplus.user.controller;

import cn.newbeedaly.mybatisplus.page.PageQueryCondition;
import cn.newbeedaly.mybatisplus.page.PageQueryResult;
import cn.newbeedaly.mybatisplus.page.PageQuerySupport;
import cn.newbeedaly.mybatisplus.user.dao.UserDao;
import cn.newbeedaly.mybatisplus.user.dao.po.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author newbeedaly
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/mp-user")
public class UserController {


    @Autowired
    private UserDao userDao;


    /**
    * 通过id查询
    */
    @GetMapping("/get/{id}")
    public User getById(@PathVariable(value = "id") Integer id){
        return userDao.getById(id);
    }

    /**
    * 新增
    */
    @PostMapping("/save")
    public boolean save(@RequestBody User user){
        return userDao.save(user);
    }

    /**
    * 通过id删除
    */
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable(value = "id") String id){
        return userDao.removeById(Integer.parseInt(id));
    }

    /**
    * 修改
    */
    @PutMapping("/update")
    public boolean updateById(@RequestBody User user){
        return userDao.updateById(user);
    }


    /**
    * 查询列表
    */
    @PostMapping("/list")
    public List<User> list(@RequestBody User user){
        List<User> list = userDao.lambdaQuery()
        //todo
        .list();
        return list;
    }

    /**
    * 分页查询
    */
    @PostMapping("/page")
    public PageQueryResult<User> page(@RequestBody PageQueryCondition<User> condition){
        QueryWrapper<User> queryWrapper = PageQuerySupport.processingSort(condition.getSortList());
        LambdaQueryWrapper<User> warrper = queryWrapper.lambda();
        // todo
        Page<User> page = userDao.page(new Page<>(condition.getPage(), condition.getSize()), warrper);
        return PageQuerySupport.buildPageResult(page);
    }

}
