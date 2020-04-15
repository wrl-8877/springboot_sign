package com.example.demo.open.exception;


import com.example.demo.open.enums.ResultCode;

/**
 * 自定义异常
 */
public class CustomException extends Exception {
    private static final long serialVersionUID = 5819174480253773214L;

    private ResultCode resultCode;
    private Exception e;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    public CustomException(ResultCode resultCode,Exception e){
        this.resultCode = resultCode;
        this.e = e;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Exception getException() {
        return e;
    }

    public void setException(Exception e) {
        this.e = e;
    }
}
