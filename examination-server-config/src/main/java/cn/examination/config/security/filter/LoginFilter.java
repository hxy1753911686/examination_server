package cn.examination.config.security.filter;

import cn.examination.config.configuration.AuthRsaKeyProperties;
import cn.examination.config.security.impl.UserDetailsImpl;
import cn.examination.config.security.utils.JwtUtils;
import cn.examination.config.vo.UserAuthVo;
import cn.examination.config.vo.UserLoginVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  自定义用户认证过滤器
 * @author hxy
 * @date 2020/9/26
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 认证管理器
     */

    private AuthenticationManager authenticationManager;

    private AuthRsaKeyProperties prop;


    public LoginFilter(AuthenticationManager authenticationManager, AuthRsaKeyProperties prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;

    }

    /**接收并解析用户凭证，并返回json数据
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        //判断请求是否为POST,禁用GET请求提交数据
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "只支持POST请求方式");
        }


        //将json数据转换为java bean对象
        try {
            UserLoginVo user = new ObjectMapper().readValue(request.getInputStream(), UserLoginVo.class);

            if (user.getUsername()==null){
                user.setUsername("");
            }

            if (user.getPassword() == null) {
                user.setPassword("");
            }
            user.getUsername().trim();
            //将用户信息交给spring security做认证操作
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()));
        }catch (Exception e) {

            throw new RuntimeException(e);
        }

    }

    /**这个方法会在验证成功时被调用
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult) {
        //获取当前登录对象
        UserAuthVo user = new UserAuthVo();
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        user.setUsername(authResult.getName());
        user.setUserCode(userDetails.getUsercode());
        user.setAvatar(userDetails.getAvater());
        user.setAuthorities((List<SimpleGrantedAuthority>) authResult.getAuthorities());

        //使用jwt创建一个token，私钥加密
        String token = JwtUtils.generateTokenExpireInMinutes(user,prop.getPrivateKey(),15);

        //返回token
        response.addHeader("Authorization","examination"+token);

        //登录成功返回json数据提示
        try {
            //生成消息
            Map<String, Object> map = new HashMap<>();
            map.put("code",HttpServletResponse.SC_OK);
            map.put("msg","登录成功");
            map.put("token","hwcms"+token);
            map.put("user",user);
            //响应数据
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(map));
            writer.flush();
            writer.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**这个方法会在验证失败时被调用
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        try {
            //生成消息
            Map<String, Object> map = new HashMap<>();
            map.put("code",HttpServletResponse.SC_UNAUTHORIZED);
            map.put("msg","用户名或密码错误");
            //响应数据
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(map));
            writer.flush();
            writer.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
