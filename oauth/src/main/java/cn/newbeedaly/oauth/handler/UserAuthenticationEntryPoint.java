package cn.newbeedaly.oauth.handler;

import cn.newbeedaly.oauth.util.HttpServletUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
     *
     * @param request       请求
     * @param response      响应
     * @param authException 凭证异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        response.setStatus(401);
        String jsonString = "凭证异常，拒绝访问";
        HttpServletUtil.printResResult(jsonString);
    }

}
