package cn.examination.domain.question;

import cn.examination.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExaminationRela extends BaseEntity<Examination> {


    private static final long serialVersionUID = 1L;

    private Long examinationId;

    private String type;

    private String question;

    private Integer questionOrder;

    private String createUser;

    private String updateUser;

    @Override
    protected Serializable pkVal() {
        return super.getId();
    }

}
