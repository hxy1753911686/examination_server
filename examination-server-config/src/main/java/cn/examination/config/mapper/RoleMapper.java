package cn.examination.config.mapper;

import cn.examination.domain.system.SysRole;
import com.baomidou.mybatisplus.annotation.SqlParser;
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
public interface RoleMapper extends BaseMapper<SysRole> {
    @SqlParser(filter = true)
    List<SysRole> selectRoleByUserId(@Param("userId") Long userId);

    @SqlParser(filter = true)
    List<SysRole> selectRoleByPermissionId(@Param("permissionId") Long permissionId);

}
