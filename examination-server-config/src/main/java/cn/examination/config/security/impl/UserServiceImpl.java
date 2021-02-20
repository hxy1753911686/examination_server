package cn.examination.config.security.impl;

import cn.examination.config.mapper.PermissionMapper;
import cn.examination.config.mapper.RoleMapper;
import cn.examination.config.mapper.UserMapper;
import cn.examination.domain.system.SysPermission;
import cn.examination.domain.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxy
 * @date 2020/9/26
 */
@Service("userService")
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ConfigUserServiceImpl configUserService;

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null){
            return null;
        }

        UserDetailsImpl user = userMapper.findByName(username);

        List<SysRole> roleList = roleMapper.selectRoleByUserId(user.getId());

        List<SimpleGrantedAuthority> list  = new ArrayList<>();
        for (SysRole sysRole : roleList) {
            List<SysPermission> permissionListItems = permissionMapper.selectPermissionByRoleId(sysRole.getId());
            for (SysPermission permission : permissionListItems) {
                list.add(new SimpleGrantedAuthority(permission.getPath()));
            }
        }
        user.setAuthorityList(list);
        return user;
    }
}
