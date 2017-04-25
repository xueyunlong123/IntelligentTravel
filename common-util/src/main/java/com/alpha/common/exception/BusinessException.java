package com.alpha.common.exception;


import com.xyl.common.enums.IErrorCode;


public class BusinessException extends BaseException {
    public BusinessException(IErrorCode iErrorCode, Throwable e) {
        super(iErrorCode, e);
    }

    public BusinessException(IErrorCode iErrorCode) {
        super(iErrorCode);
    }

    public BusinessException(String code ,String message) {
        super(code,message);
    }
}
