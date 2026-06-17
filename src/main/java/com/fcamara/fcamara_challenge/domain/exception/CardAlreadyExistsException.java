package com.fcamara.fcamara_challenge.domain.exception;

import lombok.Getter;

@Getter
public class CardAlreadyExistsException extends BusinessException {

    private final String cardNumber;
    private final String password;

    public CardAlreadyExistsException(String cardNumber, String password) {
        super(BusinessErrorCodeEnum.CARD_ALREADY_EXISTS);
        this.cardNumber = cardNumber;
        this.password = password;
    }
}
