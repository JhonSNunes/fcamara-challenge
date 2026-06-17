package com.fcamara.fcamara_challenge.domain.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final BusinessErrorCodeEnum errorCode;

    public BusinessException(BusinessErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
