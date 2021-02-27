package cn.examination.service.impl;

import cn.examination.config.mapper.ExaminationAnsdefMapper;
import cn.examination.config.mapper.ExaminationMapper;
import cn.examination.config.vo.UserAuthVo;
import cn.examination.domain.question.Examination;
import cn.examination.domain.question.ExaminationAnsdef;
import cn.examination.domain.question.ExaminationRela;
import cn.examination.service.QuestionService;
import cn.examination.vo.AnswerVo;
import cn.examination.vo.ExaminationVo;
import cn.examination.vo.QuestionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private ExaminationMapper examinationMapper;

    @Autowired
    private ExaminationAnsdefMapper examinationAnsdefMapper;

    @Override
    public List<Examination> searchAllQuestion(String createUser) {
        QueryWrapper<Examination> wrapper = new QueryWrapper<>();
        wrapper.eq("create_user",createUser);
        return examinationMapper.selectList(wrapper);
    }

    @Override
    @Transient
    public void addQuestion(ExaminationVo examinationVo) {

        //获取createUser
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthVo userAuthVo = (UserAuthVo) authentication.getPrincipal();
        String user = userAuthVo.getUserCode();

        Examination examination = new Examination();
        BeanUtils.copyProperties(examinationVo,examination);
        //生成code
        examination.setCode(UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
        examination.setCreateUser(user);
        examination.insert();

        List<QuestionVo> questionVoList = examinationVo.getQuestionVoList();
        ExaminationRela examinationRela = new ExaminationRela();
        for (QuestionVo questionVo : questionVoList) {
            BeanUtils.copyProperties(questionVo,examinationRela);
            examinationRela.setQuestionOrder(questionVo.getOrder());
            examinationRela.setCreateUser(user);
            examinationRela.setExaminationId(examination.getId());
            examinationRela.insert();

            if(examinationRela.getType() != "input" && examinationRela.getType() != "date"){
                List<AnswerVo> answerList = questionVo.getAnswer();
                for (AnswerVo answerVo : answerList) {
                    ExaminationAnsdef examinationAnsdef = new ExaminationAnsdef();
                    BeanUtils.copyProperties(answerVo,examinationAnsdef);
                    examinationAnsdef.setRelaId(examinationRela.getId());
                    examinationAnsdefMapper.insert(examinationAnsdef);
                }
            }

        }
    }
}
