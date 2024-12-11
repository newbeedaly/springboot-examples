package cn.newbeedaly.oauth.config;

import cn.newbeedaly.oauth.handler.UserAccessDeniedHandler;
import cn.newbeedaly.oauth.handler.UserAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author newbeedaly
 * @description 资源服务配置
 */
@Configuration
@EnableResourceServer
public class MyResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "res";
    private static final String SECURED_SCOPE = "#oauth2.hasScope('query')";


    @Autowired
    private UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    @Autowired
    private UserAccessDeniedHandler userAccessDeniedHandler;



    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 关闭 csrf 防护
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/res/**").access(SECURED_SCOPE);

        // 校验不通过时转给自定义类拦截处理
        http.exceptionHandling().accessDeniedHandler(userAccessDeniedHandler);

        // 校验不通过时转给自定义类拦截处理
        http.exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint);
    }
}
