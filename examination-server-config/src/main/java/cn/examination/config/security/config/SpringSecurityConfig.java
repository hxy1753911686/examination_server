package cn.examination.config.security.config;

import cn.examination.config.configuration.AuthRsaKeyProperties;
import cn.examination.config.security.common.MyAccessDeniedHandler;
import cn.examination.config.security.common.MyAuthenticationEntryPoint;
import cn.examination.config.security.filter.IdentityVerifyFilter;
import cn.examination.config.security.filter.LoginFilter;
import cn.examination.config.security.utils.NoCheckPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hxy
 * @date 2020/9/26
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private AuthRsaKeyProperties properties;

    @Bean
    public BCryptPasswordEncoder myPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**配置自定义过滤器
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //禁用跨域保护，取代它的是jwt
        http.csrf().disable();

        //允许匿名访问的方法
        http.authorizeRequests().antMatchers("/api/login").anonymous();
        //其他需要鉴权
        //.anyRequest().authenticated();

        //添加认证过滤器
        http.addFilter(new LoginFilter(authenticationManager(),properties));

        //添加验证过滤器
        http.addFilter(new IdentityVerifyFilter(authenticationManager(),properties));

        //添加自定义异常处理
        http.exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint());
        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());

        http.formLogin().loginProcessingUrl("/api/login");
//        http.logout().logoutUrl("/api/logout").logoutSuccessHandler(adminLogoutSuccessHandler).deleteCookies().clearAuthentication(true);

        //禁用session,前后端分离是无状态的
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }



    /**配置密码加密策略
     * @param authenticationManagerBuilder
     * @version 1.0
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        String admin = myPasswordEncoder().encode("admin");
//        System.err.println(admin);
//        authenticationManagerBuilder.inMemoryAuthentication().passwordEncoder(myPasswordEncoder()).withUser("admin")
//                .password(myPasswordEncoder().encode("admin")).roles("MAIN");
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(new NoCheckPasswordEncoder());
//        authenticationManagerBuilder.inMemoryAuthentication()
//                .passwordEncoder(new NoCheckPasswordEncoder())
//                .withUser("root").password(new NoCheckPasswordEncoder().encode("123123")).roles("USER");
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        //忽略静态资源
        webSecurity.ignoring().antMatchers(HttpMethod.GET,"/*.html");
    }
}
