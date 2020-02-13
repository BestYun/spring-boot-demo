package com.readchen.springbootweb.controller.vo;

public class ResultJson {

//    消息
    private String msg;
//    数据
    private Object data;
//    业务错误码0表示成功,0以上为业务错误码
    private int errCode;

    public ResultJson( int errCode,String msg, Object data) {
        this.msg = msg;
        this.data = data;
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

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

}
