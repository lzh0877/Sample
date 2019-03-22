package cn.lzh.base.bean;

/**
 * Created by LZH On 2019/3/19
 */
public class ResponseMessage {

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
