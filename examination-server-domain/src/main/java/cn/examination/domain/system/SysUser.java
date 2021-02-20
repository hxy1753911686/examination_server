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
public class SysUser extends BaseEntity<SysUser> {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String userCode;

    private String password;

    private String gender;

    private String email;

    private String avatar;

    private String userState;

    private String createUser;

    private String updateUser;

    @Override
    protected Serializable pkVal() {
        return super.getId();
    }

}
