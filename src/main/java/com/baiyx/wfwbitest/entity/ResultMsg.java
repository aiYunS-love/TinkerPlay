package com.baiyx.wfwbitest.entity;

import java.util.List;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-24 下午 09:28
 * @Description: 返回结果信息实体类
 */
public class ResultMsg {

    private String status;
    private String msg;
    private String data;
    private String code;
    private String stackTrace;
    private String requestId;
    private String success;
    private String message;

    public ResultMsg(){};

    public ResultMsg(String status, String msg, String data, String code, String stackTrace, String requestId, String success, String message) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.stackTrace = stackTrace;
        this.requestId = requestId;
        this.success = success;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
