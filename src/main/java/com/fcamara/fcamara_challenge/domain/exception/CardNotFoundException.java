package com.fcamara.fcamara_challenge.domain.exception;

public class CardNotFoundException extends BusinessException {

    public CardNotFoundException() {
        super(BusinessErrorCodeEnum.CARD_NOT_FOUND);
    }
}
