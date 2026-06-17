package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.AuthorizationException;
import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EnoughBalanceUseCase {

    public void execute(CardEntity card, BigDecimal amount) {
        Optional.of(card)
                .filter(c -> c.getBalance().compareTo(amount) >= 0)
                .orElseThrow(() -> new AuthorizationException(BusinessErrorCodeEnum.INSUFFICIENT_BALANCE));
    }
}
