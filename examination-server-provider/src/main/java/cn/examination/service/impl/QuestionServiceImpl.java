package cn.examination.service.impl;

import cn.examination.config.mapper.ExaminationAnsdefMapper;
import cn.examination.config.mapper.ExaminationAnswerMapper;
import cn.examination.config.mapper.ExaminationMapper;
import cn.examination.config.vo.UserAuthVo;
import cn.examination.domain.question.Examination;
import cn.examination.domain.question.ExaminationAnsdef;
import cn.examination.domain.question.ExaminationAnswer;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private ExaminationMapper examinationMapper;

    @Autowired
    private ExaminationAnsdefMapper examinationAnsdefMapper;

    @Autowired
    private ExaminationAnswerMapper examinationAnswerMapper;

    @Override
    public List<Examination> searchAllQuestion(String createUser) {
        QueryWrapper<Examination> wrapper = new QueryWrapper<>();
        wrapper.eq("create_user",createUser);
        return examinationMapper.selectList(wrapper);
    }

    @Override
    @Transactional
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
        examination.setCreateTime(new Date());
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

    @Override
    public ExaminationVo searchByCode(String code) {
        // 根据code，获取问卷
        Examination examination = new Examination();
        QueryWrapper<Examination> wrapper = new QueryWrapper<>();
        wrapper.eq("code",code);
        examination = (Examination) examination.selectOne(wrapper);

        ExaminationVo examinationVo = new ExaminationVo();
        if(examination.getId() != null){
            BeanUtils.copyProperties(examination,examinationVo);

            // 根据问卷id，查找rela
            ExaminationRela examinationRela = new ExaminationRela();
            QueryWrapper<ExaminationRela> relaWrapper = new QueryWrapper<>();
            relaWrapper.eq("examination_id",examination.getId());
            List<ExaminationRela> relaList = examinationRela.selectList(relaWrapper);

            List<QuestionVo> questionVoList = new ArrayList<>();
            for (ExaminationRela rela : relaList) {
                QuestionVo questionVo = new QuestionVo();
                BeanUtils.copyProperties(rela,questionVo);
                questionVo.setOrder(rela.getQuestionOrder());
                questionVo.setModel("question"+questionVo.getOrder());

                // 根据rela id，查找ansdef
                QueryWrapper<ExaminationAnsdef> ansdefWrapper = new QueryWrapper<>();
                ansdefWrapper.eq("rela_id",rela.getId());
                List<ExaminationAnsdef> examinationAnsdefList = examinationAnsdefMapper.selectList(ansdefWrapper);

                List<AnswerVo> answerVoList = new ArrayList<>();
                for (ExaminationAnsdef ansdef : examinationAnsdefList) {
                    AnswerVo answerVo = new AnswerVo();
                    BeanUtils.copyProperties(ansdef,answerVo);
                    answerVoList.add(answerVo);
                }
                questionVo.setAnswer(answerVoList);

                questionVoList.add(questionVo);
            }
            examinationVo.setQuestionVoList(questionVoList);
        }

        return examinationVo;
    }

    @Override
    @Transactional
    public void saveAnswer(List<ExaminationAnswer> examinationAnswerList) {
        for (ExaminationAnswer examinationAnswer : examinationAnswerList) {
            examinationAnswerMapper.insert(examinationAnswer);
        }
    }
}
