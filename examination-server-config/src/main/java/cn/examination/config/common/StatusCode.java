package cn.examination.config.common;

public enum StatusCode {
    //成功
    SUCCESS( 200, "SUCCESS" ),
    //失败
    FAILURE( 400, "FAILED" ),
    // 未登录
    UN_LOGIN( 401, "没有权限" ),
    //未认证（签名错误、token错误）
    UNAUTHORIZED( 403, "未认证或Token失效" ),
    //未通过认证
    USER_UNAUTHORIZED( 402, "用户名或密码不正确" ),
    //接口不存在
    NOT_FOUND( 404, "请求地址不存在" ),
    //服务器内部错误
    INTERNAL_SERVER_ERROR( 500, "服务器内部错误" ),
    TOKEN_EXPIRED( 50014, "token过期" );

    private int code;
    private String desc;

    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }


    public String getDesc() {
        return desc;
    }


}
