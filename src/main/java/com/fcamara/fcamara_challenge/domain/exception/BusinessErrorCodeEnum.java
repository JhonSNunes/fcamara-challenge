package com.fcamara.fcamara_challenge.domain.exception;

import lombok.Getter;

@Getter
public enum BusinessErrorCodeEnum {
    INSUFFICIENT_BALANCE("SALDO_INSUFICIENTE"),
    INVALID_PASSWORD("SENHA_INVALIDA"),
    CARD_NOT_FOUND("CARTAO_INEXISTENTE");

    private final String message;

    BusinessErrorCodeEnum(String message) {
        this.message = message;
    }
}
