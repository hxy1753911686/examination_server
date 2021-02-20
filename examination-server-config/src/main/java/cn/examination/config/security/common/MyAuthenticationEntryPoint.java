package cn.examination.config.security.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 401异常处理公共类
 * @author hxy
 * @date 2020/9/26
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        response.setStatus(200);
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        map.put("msg","令牌已过期请重新登录");

        ServletOutputStream out = response.getOutputStream();
        String s = new ObjectMapper().writeValueAsString(map);
        byte[] bytes = s.getBytes();
        out.write(bytes);
    }
}
