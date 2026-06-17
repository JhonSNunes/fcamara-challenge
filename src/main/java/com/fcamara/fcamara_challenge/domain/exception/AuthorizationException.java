package com.fcamara.fcamara_challenge.domain.exception;

public class AuthorizationException extends BusinessException {

    public AuthorizationException(BusinessErrorCodeEnum errorCode) {
        super(errorCode);
    }
}
