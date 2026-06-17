package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.CardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GetCardBalanceUseCase {

    private final CardProvider cardProvider;

    public BigDecimal execute(String cardNumber) {
        return cardProvider.findByCardNumber(cardNumber)
                .map(CardEntity::getBalance)
                .orElseThrow(CardNotFoundException::new);
    }
}
