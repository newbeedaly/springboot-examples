package cn.newbeedaly.oauth.handler;

import cn.newbeedaly.oauth.util.HttpServletUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
     *
     * @param request               请求
     * @param response              响应
     * @param accessDeniedException 访问异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        response.setStatus(403);
        String jsonString = "访问受限";
        HttpServletUtil.printResResult(jsonString);
    }

}
