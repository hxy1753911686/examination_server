package cn.examination.config.security.filter;

import cn.examination.config.configuration.AuthRsaKeyProperties;
import cn.examination.config.security.utils.JwtUtils;
import cn.examination.config.security.utils.Payload;
import cn.examination.config.vo.UserAuthVo;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hxy
 * @date 2020/9/26
 */
public class IdentityVerifyFilter extends BasicAuthenticationFilter {

    private AuthRsaKeyProperties prop;

    public IdentityVerifyFilter(AuthenticationManager authenticationManager, AuthRsaKeyProperties prop) {
        super(authenticationManager);
        this.prop = prop;
    }

    /**过滤请求
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain) throws ServletException, IOException, AuthenticationException, ExpiredJwtException {

        //判断请求体的头中是否包含Authorization
        String authorization = request.getHeader("Authorization");
        //Authorization中是否包含hwcms，不包含直接返回
        if (authorization==null||!authorization.startsWith("examination")){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token;
        try {
            //解析jwt生成的token，获取权限
            token = getAuthentication(authorization);

        }catch (ExpiredJwtException e){
            // e.printStackTrace();
            chain.doFilter(request, response);
            return;
        }

        //获取后，将Authentication写入SecurityContextHolder中供后序使用
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);


    }



    /**对jwt生成的token进行解析
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String authorization) throws ExpiredJwtException{

        if (authorization == null) {
            return null;
        }

        Payload<UserAuthVo> payload;

        //从token中获取有效载荷
        payload = JwtUtils.getInfoFromToken(authorization.replace("examination", ""), prop.getPublicKey(), UserAuthVo.class);



        //获取当前访问对象
        UserAuthVo userInfo = payload.getUserInfo();
        if (userInfo == null){
            return null;
        }

        //将当前访问对象及其权限封装称spring security可识别的token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userInfo,null,userInfo.getAuthorities());
        return token;
    }
}
