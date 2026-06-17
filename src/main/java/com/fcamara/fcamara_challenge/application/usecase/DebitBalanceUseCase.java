package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DebitBalanceUseCase {

    private final CardProvider cardProvider;

    public void execute(CardEntity card, BigDecimal amount) {
        card.debit(amount);
        cardProvider.save(card);
    }
}
