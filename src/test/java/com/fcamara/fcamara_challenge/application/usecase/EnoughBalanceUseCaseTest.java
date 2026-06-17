package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.AuthorizationException;
import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnoughBalanceUseCaseTest {

    private final EnoughBalanceUseCase enoughBalanceUseCase = new EnoughBalanceUseCase();

    @Test
    void shouldPassWhenBalanceIsGreaterThanAmount() {
        CardEntity card = CardTestFixtures.cardWithBalance(new BigDecimal("100.00"));

        assertThatCode(() -> enoughBalanceUseCase.execute(card, new BigDecimal("10.00")))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldPassWhenBalanceEqualsAmount() {
        CardEntity card = CardTestFixtures.cardWithBalance(new BigDecimal("10.00"));

        assertThatCode(() -> enoughBalanceUseCase.execute(card, new BigDecimal("10.00")))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowWhenBalanceIsInsufficient() {
        CardEntity card = CardTestFixtures.cardWithBalance(new BigDecimal("9.99"));

        assertThatThrownBy(() -> enoughBalanceUseCase.execute(card, new BigDecimal("10.00")))
                .isInstanceOf(AuthorizationException.class)
                .extracting("errorCode")
                .isEqualTo(BusinessErrorCodeEnum.INSUFFICIENT_BALANCE);
    }
}
