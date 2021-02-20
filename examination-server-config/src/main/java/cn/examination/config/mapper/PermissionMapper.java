package cn.examination.config.mapper;

import cn.examination.domain.system.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hanxiangyu
 * @since 2020-06-07
 */
public interface PermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermission> selectPermissionByRoleId(@Param("roleId")Long roleId);
}
