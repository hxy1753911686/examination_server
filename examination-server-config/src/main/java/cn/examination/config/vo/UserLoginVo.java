package cn.examination.config.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录的请求参数
 * TODO：后续将添加部门
 * @author hxy
 * @date 2020/9/26
 */
@Data
public class UserLoginVo implements Serializable {

    private String username;
    private String password;
}
