package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.CardAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCardUseCase {

    private final CardProvider cardProvider;

    public CardEntity execute(String cardNumber, String password) {
        cardProvider.findByCardNumber(cardNumber)
                .ifPresent(card -> {
                    throw new CardAlreadyExistsException(cardNumber, password);
                });

        return cardProvider.save(CardEntity.create(cardNumber, password));
    }
}
