package cn.examination.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExaminationVo {
    private Long id;

    private String title;

    private List<QuestionVo> questionVoList;

}
