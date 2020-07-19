package com.tuozuo.tavern.common.protocol;


import static com.tuozuo.tavern.common.protocol.RetCodeDict.AUTH_FAILURE;

/**
 * Created by 刘悦之 on 2019/6/30.
 */
public class TavernResponse<T> {

    int code;
    String msg;
    T data;
    public static final TavernResponse OK = new TavernResponse(0, "ok", null);
    public static final TavernResponse AUTH_FAILED = new TavernResponse(AUTH_FAILURE, "token timeout", null);

    public static <T> TavernResponse<T> ok(T data) {
        return new TavernResponse(0, "OK", data);
    }

    public static <T> TavernResponse<T> createResponse(int code, String message, T data) {
        return new TavernResponse(code, message, data);
    }

    public static <T> TavernResponse<T> bizFailure(int code,String message) {
        return new TavernResponse(code, message, null);
    }

    public static <T> TavernResponse<T> bizFailure(String message) {
        return new TavernResponse(RetCodeDict.BIZ_FAILURE, message, null);
    }

    private TavernResponse() {
    }

    public TavernResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
