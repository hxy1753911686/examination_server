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
public class SysPermission extends BaseEntity<SysPermission> {

    private static final long serialVersionUID = 1L;

    private String name;

    private String type;

    private String path;

    private String resources;

    private String title;

    private Long parentId;

    private String component;

    private String hidden;

    private Boolean onCache;

    private Integer sort;

    private Integer icon;

    private String description;

    @Override
    protected Serializable pkVal() {
        return super.getId();
    }

}
