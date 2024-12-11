package cn.newbeedaly.shiro.shiro;

import cn.newbeedaly.shiro.bean.User;
import cn.newbeedaly.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

/**
 * 自定义Realm
 *
 * @author newbeedaly
 * @date 2019/7/17
 */
public class UserRealm extends AuthorizingRealm {


    @Resource
    private UserService userService;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑");

        // 给资源授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // info.addStringPermission("user:add");

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal(); //getPrincipal 登陆认证时传入的第一个参数，此处为User
        User dbUser = userService.findById(user.getId());

        info.addStringPermission(dbUser.getPerms());

        return info;
    }


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        // 登陆认证操作
        // 判断用户名和密码

        // 假设用户名和密码
        //String username = "newbeedaly";
        //String password = "123";


        // 判断用户名
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;

        User user = userService.findByName(utoken.getUsername());

        if (user == null) {
            return null;
            // shiro 底层此处抛出UnknownAccountException

        }

        // 判断密码
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");

    }
}
