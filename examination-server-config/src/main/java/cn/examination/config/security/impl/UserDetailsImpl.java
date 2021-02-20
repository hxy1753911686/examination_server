package cn.examination.config.security.impl;

import cn.examination.domain.system.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author hxy
 * @date 2020/9/26
 */
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String usercode;
    private String avater;

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    private List<SimpleGrantedAuthority> authorityList;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(SysUser user, List<SimpleGrantedAuthority> authorityList) {
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.authorityList = authorityList;
    }

    public UserDetailsImpl(SysUser user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
    }

    public void setAuthorityList(List<SimpleGrantedAuthority> authorityList) {
        this.authorityList = authorityList;
    }


    @Override
    //返回用户所有角色的封装，一个Role对应一个GrantedAuthority
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    //判断账号是否过期，from redis
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    //判断账号是否被锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    //判断信用凭证是否过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //判断账号是否可用
    public boolean isEnabled() {
        return true;
    }
}
