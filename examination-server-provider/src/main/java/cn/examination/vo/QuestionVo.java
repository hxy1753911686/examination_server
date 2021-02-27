package cn.examination.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVo {
    private Long id;
    private Integer order;
    private String question;
    private String type;
    private List<AnswerVo> answer;
}
