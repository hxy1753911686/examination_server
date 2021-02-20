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
 * @author hanxiangyu
 * @since 2020-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysRole extends BaseEntity<SysRole> {

    private static final long serialVersionUID = 1L;

    private String roleCode;

    private String roleName;

    @Override
    protected Serializable pkVal() {
        return super.getId();
    }

}
