package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionUseCase {

    private final CardExistsUseCase cardExistsUseCase;
    private final PasswordMatchesUseCase passwordMatchesUseCase;
    private final EnoughBalanceUseCase enoughBalanceUseCase;
    private final DebitBalanceUseCase debitBalanceUseCase;

    @Transactional
    public void execute(String cardNumber, String cardPassword, BigDecimal amount) {
        CardEntity card = cardExistsUseCase.execute(cardNumber);
        passwordMatchesUseCase.execute(card, cardPassword);
        enoughBalanceUseCase.execute(card, amount);
        debitBalanceUseCase.execute(card, amount);
    }
}
