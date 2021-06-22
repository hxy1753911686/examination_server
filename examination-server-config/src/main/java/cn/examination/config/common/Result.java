package cn.examination.config.common;

public class Result {

    private Integer code = StatusCode.SUCCESS.getCode();
    /**
     * 消息内容
     */
    private String message = "";
    /**
     * 返回数据
     */
    private Object data = null;

    private static ThreadLocal<Result> resultLocalVar = new ThreadLocal<>();

    // 每个线程一个Result
    public static synchronized Result getLocalObject(){
        if(null == resultLocalVar.get()){
            resultLocalVar.set(new Result());
        }
        return resultLocalVar.get();
    }

    public Result(){

    }

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /***
     *
     * @description: 清空threadLocal中数据，为了防止内存泄漏，在结束请求之前需要调用
     * @param
     * @return void
     * @author: hanxiangyu
     * @dateTime: 2021/6/22 7:01 下午
     */
    public static void clear(){
        resultLocalVar.remove();
    }

    /***
     *
     * @description: 向result对象塞值
     * @param code
     * @param message
     * @param data
     * @return void
     * @author: hanxiangyu
     * @dateTime: 2021/6/22 7:07 下午
     */
    public static void putValue(Integer code, String message, Object data){
        Result result = getLocalObject();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
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
