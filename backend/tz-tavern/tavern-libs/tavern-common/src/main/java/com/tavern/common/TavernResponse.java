package com.tavern.common;

/**
 * Created by 刘悦之 on 2019/6/30.
 */
public class TavernResponse {

    private int code = RetCodeDict.OK_CODE;
    private static Object msg = "ok";
    private Object data;

    public TavernResponse() {
    }

    public static TavernResponse failure(Object errors) {
        TavernResponse response = new TavernResponse();
        response.setCode(RetCodeDict.COMMON_FAILURE);
        response.setMsg(errors);
        return response;
    }

    public static TavernResponse failure(int code, Object errors) {
        TavernResponse response = new TavernResponse();
        response.setCode(code);
        response.setMsg(errors);
        return response;
    }

    public static TavernResponse success() {
        TavernResponse response = new TavernResponse();
        response.setCode(RetCodeDict.OK_CODE);
        response.setMsg(msg);
        return response;
    }

    public static TavernResponse success(Object data) {
        TavernResponse response = new TavernResponse();
        response.setCode(RetCodeDict.OK_CODE);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
