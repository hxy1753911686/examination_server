package cn.examination.config.common;

public class Result {
    private Integer code;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data = null;

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result expired(String message) {
        return new Result(StatusCode.TOKEN_EXPIRED.getCode(), message);
    }

    public static Result fail(String message) {
        return new Result(StatusCode.FAILURE.getCode(), message);
    }

    public static Result fail(Integer code, String message) {
        return new Result(code, message);
    }

    public static Result fail() {
        return new Result(StatusCode.FAILURE.getCode(), "fail");
    }

    public static Result success() {
        return new Result(StatusCode.SUCCESS.getCode(), "success");
    }

    public static Result success(String message) {
        return new Result(StatusCode.SUCCESS.getCode(), message);
    }

    public static Result success(Object data) {
        return new Result(StatusCode.SUCCESS.getCode(), "success", data);
    }

    public static Result success(String message, Object data) {
        return new Result(StatusCode.SUCCESS.getCode(), message, data);
    }

    public static Result build(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result build(Integer code, String msg) {
        return new Result(code, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object result) {
        this.data = result;
    }


}
