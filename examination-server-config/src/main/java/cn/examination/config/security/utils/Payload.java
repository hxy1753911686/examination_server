package cn.examination.config.security.utils;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装token中的用户信息
 * @author hxy
 * @date 2020/9/26
 */
@Data
public class Payload<T> implements Serializable {

    /**
     * token id
     */
    private String id;

    /**
     * 用户信息（用户名、角色...）
     */
    private T userInfo;

    /**
     * 令牌过期时间
     */
    private Date expiration;


}
