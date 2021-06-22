package cn.examination.controller;

import cn.examination.config.common.Result;
import cn.examination.config.security.utils.JsonUtils;
import cn.examination.config.vo.UserAuthVo;
import cn.examination.domain.question.Examination;
import cn.examination.domain.question.ExaminationAnswer;
import cn.examination.service.QuestionService;
import cn.examination.vo.ExaminationVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/question")
@Api(tags ={"问卷调查"})
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 获取问卷集合
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取问卷集合",response = Result.class)
    public Result list(HttpServletRequest request) {
        Authentication authentication = (Authentication) request.getUserPrincipal();
        UserAuthVo userAuthVo = (UserAuthVo) authentication.getPrincipal();
        String name = userAuthVo.getUserCode();
        List<Examination> examinationList = questionService.searchAllQuestion(name);
        return Result.success(examinationList);
    }



    /**
     * 新增问卷
     */
    @PostMapping("/add")
    public Result add(@RequestBody ExaminationVo examinationVo) {
//        ExaminationVo examinationVo1 = new ExaminationVo();
//        ExaminationVo examinationVo = JsonUtils.toBean(jsonArray.toJSONString(),ExaminationVo.class);
        questionService.addQuestion(examinationVo);
        return Result.success();
    }

    /**
     * 查看单个问卷信息
     */
    @PostMapping("/getInfo")
    public Result searchByCode(@RequestBody String code){
        ExaminationVo examinationVo = questionService.searchByCode(code);
        return Result.success(examinationVo);
    }

    /**
     * 保存问卷答案
     */
    @PostMapping("/saveAnswer")
    public Result saveAnswer(@RequestBody List<ExaminationAnswer> examinationAnswerList){
        questionService.saveAnswer(examinationAnswerList);
        for (ExaminationAnswer examinationAnswer : examinationAnswerList) {
            System.err.println(examinationAnswer);
        }
        return Result.success();
    }


}
