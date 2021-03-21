package cn.examination.config.security.auth;

import cn.examination.config.common.Result;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 权限认证入口，token错误或者token失效都会在此被拦截
 * @Author:hanxiangyu
 * @date 2020/03/21
 */
@Slf4j
@Component
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if(e != null){
            this.out(httpServletResponse, Result.expired(e.getMessage()));
        }else{
            this.out(httpServletResponse, Result.expired("登录过期!"));
        }
    }

    private void out(ServletResponse response, Result result){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSON(result));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }
}
