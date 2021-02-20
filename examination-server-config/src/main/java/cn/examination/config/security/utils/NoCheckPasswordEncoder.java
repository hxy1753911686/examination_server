package cn.examination.config.security.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author hxy
 * @date 2020/9/27
 */
public class NoCheckPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        //不加密
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        System.err.print(charSequence);
        System.err.print(s);
        if(charSequence.toString().equals(s)){
            return true;
        }
        return false;
    }
}
