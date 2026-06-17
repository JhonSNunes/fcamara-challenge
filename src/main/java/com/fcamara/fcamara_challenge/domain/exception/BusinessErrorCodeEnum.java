package com.fcamara.fcamara_challenge.domain.exception;

import lombok.Getter;

@Getter
public enum BusinessErrorCodeEnum {
    INSUFFICIENT_BALANCE(Codes.INSUFFICIENT_BALANCE),
    INVALID_PASSWORD(Codes.INVALID_PASSWORD),
    CARD_NOT_FOUND(Codes.CARD_NOT_FOUND),
    CARD_ALREADY_EXISTS(Codes.CARD_ALREADY_EXISTS);

    public static final class Codes {

        public static final String INSUFFICIENT_BALANCE = "SALDO_INSUFICIENTE";
        public static final String INVALID_PASSWORD = "SENHA_INVALIDA";
        public static final String CARD_NOT_FOUND = "CARTAO_INEXISTENTE";
        public static final String CARD_ALREADY_EXISTS = "CARTAO_JA_EXISTENTE";

        private Codes() {
        }
    }

    private final String message;

    BusinessErrorCodeEnum(String message) {
        this.message = message;
    }
}
