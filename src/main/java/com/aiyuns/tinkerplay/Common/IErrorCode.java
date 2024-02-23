package com.aiyuns.tinkerplay.Common;

/**
 * API返回码接口
 */
public interface IErrorCode {
    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMessage();
}
