package cn.examination.config.mapper;

import cn.examination.config.security.impl.UserDetailsImpl;
import cn.examination.domain.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hanxiangyu
 * @since 2020-06-07
 */
public interface UserMapper extends BaseMapper<SysUser> {

    UserDetailsImpl findByName(@Param("name")String name);

}
