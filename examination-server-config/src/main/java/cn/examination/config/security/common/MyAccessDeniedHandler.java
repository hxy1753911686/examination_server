package cn.examination.config.security.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 403异常处理
 *
 * @author hxy
 * @date 2020/9/26
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(200);
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpServletResponse.SC_FORBIDDEN);
        map.put("msg", "未授权访问此资源，如有需要请联系管理员授权");
        ServletOutputStream out = response.getOutputStream();
        String s = new ObjectMapper().writeValueAsString(map);
        byte[] bytes = s.getBytes();
        out.write(bytes);
    }
}
