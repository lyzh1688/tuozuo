package com.tuozuo.tavern.common.protocol;


/**
 * Created by 刘悦之 on 2019/6/30.
 */
public class TavernResponse<T> {

    int retCode;
    String message;
    T data;
    public static final TavernResponse OK = new TavernResponse(0, "OK", null);
    public static final TavernResponse AUTH_FAILED = new TavernResponse(1000, "token timeout", null);

    public static <T> TavernResponse<T> ok(T data) {
        return new TavernResponse(0, "OK", data);
    }

    public static <T> TavernResponse<T> createResponse(int code, String message, T data) {
        return new TavernResponse(code, message, data);
    }

    public static <T> TavernResponse<T> bizFailure(String message) {
        return new TavernResponse(RetCodeDict.BIZ_FAILURE, message, null);
    }

    private TavernResponse() {
    }

    public TavernResponse(int retCode, String message, T data) {
        this.retCode = retCode;
        this.message = message;
        this.data = data;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
