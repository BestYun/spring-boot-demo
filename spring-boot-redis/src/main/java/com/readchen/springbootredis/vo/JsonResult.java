package com.readchen.springbootredis.vo;


public class JsonResult {

    private int errCode;
    private String msg;
    private Object data;

    public JsonResult(int errCode, String msg, Object data) {
        this.errCode = errCode;
        this.msg = msg;
        this.data = data;
    }

    public static JsonResult success(int errCode, String msg, Object data){
        return new JsonResult(errCode,msg,data);
    }
    public static JsonResult success(Object data){
        return new JsonResult(0,"ok",data);
    }
    public static JsonResult success(int errCode, Object data){
        return new JsonResult(errCode,"ok",data);
    }
    public static JsonResult fail(int errCode,  Object data){
        return new JsonResult(errCode,"ok",data);
    }
    public static JsonResult fail(int errCode, String msg){
        return new JsonResult(errCode,msg,null);
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
