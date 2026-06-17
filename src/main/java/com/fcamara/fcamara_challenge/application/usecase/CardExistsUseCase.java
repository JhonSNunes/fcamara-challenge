package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.AuthorizationException;
import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardExistsUseCase {

    private final CardProvider cardProvider;

    public CardEntity execute(String cardNumber) {
        return cardProvider.findByCardNumberForUpdate(cardNumber)
                .orElseThrow(() -> new AuthorizationException(BusinessErrorCodeEnum.CARD_NOT_FOUND));
    }
}
