package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.AuthorizationException;
import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordMatchesUseCase {

    public void execute(CardEntity card, String cardPassword) {
        Optional.of(card)
                .filter(c -> c.getPassword().equals(cardPassword))
                .orElseThrow(() -> new AuthorizationException(BusinessErrorCodeEnum.INVALID_PASSWORD));
    }
}
