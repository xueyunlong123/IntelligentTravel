package com.alpha.common.exception;

import com.xyl.common.enums.IErrorCode;

/**
 * Created by cw on 15-11-20.
 */
public class DatabaseException extends BaseException {
    public DatabaseException(IErrorCode iErrorCode, Throwable e) {
        super(iErrorCode, e);
    }

    public DatabaseException(IErrorCode iErrorCode) {
        super(iErrorCode);
    }
}
