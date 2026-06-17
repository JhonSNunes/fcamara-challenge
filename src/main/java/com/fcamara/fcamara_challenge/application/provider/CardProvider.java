package com.fcamara.fcamara_challenge.application.provider;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;

import java.util.Optional;

public interface CardProvider {

    Optional<CardEntity> findByCardNumberForUpdate(String cardNumber);

    Optional<CardEntity> findByCardNumber(String cardNumber);

    CardEntity save(CardEntity card);
}
