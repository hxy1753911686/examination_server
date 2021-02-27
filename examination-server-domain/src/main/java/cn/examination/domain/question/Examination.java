package cn.examination.domain.question;

import cn.examination.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Examination extends BaseEntity<Examination> {

    private static final long serialVersionUID = 1L;

    private String title;

    private String code;

    private String createUser;

    private String updateUser;


    @Override
    protected Serializable pkVal() {
        return super.getId();
    }

}
