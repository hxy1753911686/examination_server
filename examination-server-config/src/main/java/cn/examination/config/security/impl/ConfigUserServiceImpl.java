package cn.examination.config.security.impl;

import cn.examination.config.mapper.UserMapper;
import cn.examination.config.vo.UserAuthVo;
import cn.examination.config.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author hxy
 * @date 2020/10/14
 */
@Service
public class ConfigUserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    public void getUserInfo(UserInfoVo userInfoVo, UserAuthVo userAuthVo) {
        Collection<? extends GrantedAuthority> authorities = userAuthVo.getAuthorities();
        for (GrantedAuthority authority : authorities) {
//            String roleCode = authority.getAuthority();
//            SysRole sysRole = configRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_code", roleCode).eq("tenant_id", securityUser.getTenantId()));
//            //查询角色下权限列表
//            List<SysAuthority> authoritys = configRoleAuthorityMapper.findAuthoritysByRoleId(sysRole.getId());
//            if (authoritys != null && !authoritys.isEmpty()) {
//                authoritys.stream().filter(Objects::nonNull).forEach(auth -> {
//                    if (StrUtil.isNotBlank(auth.getComponent())) {
//                        AuthorityVO menuVO = new AuthorityVO();
//                        BeanUtil.copyProperties(auth, menuVO);
//                        authorityVOS.add(menuVO);
//                    }
//                    if (StrUtil.isNotBlank(auth.getResources())) {
//                        ButtonVO buttonVO = new ButtonVO();
//                        BeanUtil.copyProperties(auth, buttonVO);
//                        buttonVOS.add(buttonVO);
//                    }
//                });
//            }
        }
    }
}
