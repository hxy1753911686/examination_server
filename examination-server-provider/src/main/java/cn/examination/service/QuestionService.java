package cn.examination.service;

import cn.examination.domain.question.Examination;
import cn.examination.vo.ExaminationVo;

import java.util.List;

public interface QuestionService {
    List<Examination> searchAllQuestion(String createUser);

    void addQuestion(ExaminationVo examinationVo);
}
