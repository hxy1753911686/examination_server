package cn.examination.config.vo;

import cn.examination.config.security.utils.AuthDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxy
 * @date 2020/9/26
 */
@Data
public class UserAuthVo implements Serializable {

    @JsonDeserialize(using = AuthDeserializer.class)
    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    private String username;
    private String userCode;
    private String avatar;
    private List<SimpleGrantedAuthority> authorities;
}
