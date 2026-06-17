package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.AuthorizationException;
import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordMatchesUseCaseTest {

    private final PasswordMatchesUseCase passwordMatchesUseCase = new PasswordMatchesUseCase();

    @Test
    void shouldPassWhenPasswordMatches() {
        CardEntity card = CardTestFixtures.cardWithBalance(CardEntity.INITIAL_BALANCE);

        assertThatCode(() -> passwordMatchesUseCase.execute(card, CardTestFixtures.PASSWORD))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowWhenPasswordDoesNotMatch() {
        CardEntity card = CardTestFixtures.cardWithBalance(CardEntity.INITIAL_BALANCE);

        assertThatThrownBy(() -> passwordMatchesUseCase.execute(card, "9999"))
                .isInstanceOf(AuthorizationException.class)
                .extracting("errorCode")
                .isEqualTo(BusinessErrorCodeEnum.INVALID_PASSWORD);
    }
}
