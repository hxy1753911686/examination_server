package cn.examination.domain.system;

import cn.examination.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hanxaingyu
 * @since 2020-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysRolePermission extends BaseEntity<SysRolePermission> {

    private static final long serialVersionUID = 1L;


    private Long roleId;

    private Long permissionId;


    @Override
    protected Serializable pkVal() {
        return super.getId();
    }

}
