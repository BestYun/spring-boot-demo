package com.readchen.springbootswagger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("返回结果")
public class JsonResult<T> {

    @ApiModelProperty("业务错误码，0表示成功，大于0表示错误")
    private int errCode;
    @ApiModelProperty("提示")
    private String msg;
    @ApiModelProperty("数据")
    private T data;

    public JsonResult(int errCode, String msg, T data) {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
