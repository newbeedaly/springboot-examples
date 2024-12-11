package cn.newbeedaly.mybatisplus.use;

import cn.newbeedaly.mybatisplus.user.dao.mapper.UserMapper;
import cn.newbeedaly.mybatisplus.user.dao.po.User;
import cn.newbeedaly.mybatisplus.user.service.impl.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: newbeedaly
 * @date: 2020/8/23
 **/
@SpringBootTest
public class SimpleTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    public void testSelectList() {
        // todo AR 模式简单了解下。

        System.out.println(("----- testSelectList method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        System.out.println(("----- testInsert method test ------"));
        // 主键默认id为雪花算法生成。 自动转换 userName -> user_name
        // 默认忽略null字段的插入
        User user = User.builder().userName("向北").age(26).userName("newbeedadly@gmail.com")
                //.remark("非持久化字段")
                .build();
        int row = userMapper.insert(user);
        System.out.println("影响记录数:" + row);
    }

    @Test
    public void testSelect() {
        System.out.println(("----- testSelect method test ------"));
        User user = userMapper.selectById(1297348910636883970L);
        System.out.println(user);

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1297348910636883970L, 1L));
        System.out.println(users);

        Map<String, Object> map = new HashMap<>();
        map.put("id", 1L);
        users = userMapper.selectByMap(map);
        System.out.println(users);
    }

    @Test
    public void testSelectByWrapper() {
        System.out.println(("----- testSelectByWrapper method test ------"));
        QueryWrapper<User> query = Wrappers.query();
        QueryWrapper<User> queryWrapper = query.gt("age", 20).orderByDesc("age");

        List<User> users = userMapper.selectList(queryWrapper);
        users.stream().forEach(System.out::println);

        QueryWrapper<User> userQuery = Wrappers.query();
        QueryWrapper<User> userQueryWrapper = userQuery.like("name", "newbee")
                .and(wq -> wq.gt("age", 18).or().isNotNull("email"))
                .orderByDesc("age");

        users = userMapper.selectList(userQueryWrapper);
        users.stream().forEach(System.out::println);

    }

    @Test
    public void testSelectByWrapperSupper() {
        System.out.println(("----- testSelectByWrapperSupper method test ------"));
        QueryWrapper<User> query = new QueryWrapper<>();
        QueryWrapper<User> queryWrapper = query.select("id", "name")
                .gt("age", 20).orderByDesc("age");

        List<User> users = userMapper.selectList(queryWrapper);
        users.stream().forEach(System.out::println);

    }

    @Test
    public void testSelectByWrapperCondition() {
        System.out.println(("----- testSelectByWrapperCondition method test ------"));
        QueryWrapper<User> query = new QueryWrapper<>();
        String name = "newbee";
        QueryWrapper<User> queryWrapper = query.like(StringUtils.isNotBlank(name), "name", name).orderByDesc("age");

        List<User> users = userMapper.selectList(queryWrapper);
        users.stream().forEach(System.out::println);

    }

    @Test
    public void testSelectByWrapperEntity() {
        System.out.println(("----- testSelectByWrapperCondition method test ------"));
        User conditionUser = new User();
        // 默认为Equal
        conditionUser.setUserName("newbeedaly");
        QueryWrapper<User> query = new QueryWrapper<>(conditionUser);
        String name = "newbee";
        QueryWrapper<User> queryWrapper = query.like(StringUtils.isNotBlank(name), "name", name).orderByDesc("age");

        List<User> users = userMapper.selectList(queryWrapper);
        users.stream().forEach(System.out::println);

    }

    @Test
    public void testSelectByWrapperMaps() {
        System.out.println(("----- testSelectByWrapperCondition method test ------"));
        User conditionUser = new User();
        // 默认为Equal
        conditionUser.setUserName("newbeedaly");
        QueryWrapper<User> query = new QueryWrapper<>(conditionUser);
        String name = "newbee";
        QueryWrapper<User> queryWrapper = query.select("id", "name")
                .like(StringUtils.isNotBlank(name), "name", name).orderByDesc("age");
        // 另外对于分组查询时，可以使用selectMaps方法
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.stream().forEach(System.out::println);
        //selectObjs 只查询1列
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.stream().forEach(System.out::println);
        //selectCount 查询天数
        Integer integer = userMapper.selectCount(queryWrapper);
        //selectCount 查询一个，多个出错
        //User user = userMapper.selectOne(queryWrapper);

    }

    @Test
    public void testSelectByLambdaWrapper() {
        System.out.println(("----- testSelectByLambdaWrapper method test ------"));
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        LambdaQueryWrapper<User> newbee = lambdaQuery.like(User::getUserName, "newbee");
        List<User> userList = userMapper.selectList(newbee);
        userList.stream().forEach(System.out::println);
        // 另一种用法
        LambdaQueryChainWrapper<User> lambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(userMapper);
        List<User> users = lambdaQueryChainWrapper.like(User::getUserName, "newbee").list();
        users.stream().forEach(System.out::println);
    }

    @Test
    public void testSelectByLambdaMineSql() {
        System.out.println(("----- testSelectByLambdaWrapper method test ------"));
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        LambdaQueryWrapper<User> newbee = lambdaQuery.like(User::getUserName, "newbee");
        List<User> userList = userMapper.getAll(newbee);
        userList.stream().forEach(System.out::println);

    }

    @Test
    public void testSelectIservice() {
        System.out.println(("----- testSelectIservice method test ------"));
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        LambdaQueryWrapper<User> lambdaQueryWrapper = lambdaQuery.like(User::getUserName, "newbee");
        userService.list(lambdaQueryWrapper);
    }

    @Test
    public void testSelectPage() {
        System.out.println(("----- testSelectIservice method test ------"));
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        LambdaQueryWrapper<User> lambdaQueryWrapper = lambdaQuery.ge(User::getAge, 20);
        // 同样可以使用userMapper的selectPage方法
        Page<User> page = new Page<>(1, 2);
        //Page<User> page = new Page<>(1, 2, false); // 不查总记录数
        IPage<User> iPage = userService.page(page, lambdaQueryWrapper);
        System.out.println(iPage);

        // 自定义
        iPage = userMapper.getUserPage(page, lambdaQueryWrapper);
        System.out.println(iPage);

    }

    @Test
    public void testUpdate() {
        System.out.println(("----- testUpdate method test ------"));
        User user = new User();
        user.setId(1L);
        user.setAge(22);
        //user.setEmail(null); // null 不进行更新
        boolean b = userService.updateById(user);
        //int i = userMapper.updateById(user);
        System.out.println(b);

        User whereUser = new User();
        whereUser.setUserName("newbeedaly");

        LambdaUpdateWrapper<User> updateLambda = Wrappers.lambdaUpdate(whereUser);
        updateLambda.eq(User::getAge, 26);

        user = new User();
        user.setAge(26);
        boolean b1 = userService.update(user, updateLambda);
        System.out.println(b1);

        updateLambda = Wrappers.lambdaUpdate(whereUser);
        updateLambda.eq(User::getAge, 26)
                .set(User::getAge, 28);

        boolean update = userService.update(updateLambda);
        System.out.println(update);
    }

    @Test
    public void testUpdateNull() {
        System.out.println(("----- testUpdateNull method test ------"));
        User user = new User(); // 建议此处是数据库中查询的pojo，否则不传user对象
        user.setId(1L); // 被忽略
        user.setUserName("Jone");
        LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.lambdaUpdate();
        lambdaUpdate.set(User::getAge, null);
        lambdaUpdate.eq(User::getId, 1L);
        boolean b = userService.update(user, lambdaUpdate);
        System.out.println(b);
    }

    @Test
    public void testDelete() {
        System.out.println(("----- testDelete method test ------"));
        boolean b = userService.removeById(100L);

    }
}
