package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.AuthorizationException;
import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardExistsUseCaseTest {

    @Mock
    private CardProvider cardProvider;

    @InjectMocks
    private CardExistsUseCase cardExistsUseCase;

    @Test
    void shouldReturnCardWhenItExists() {
        CardEntity card = CardTestFixtures.cardWithBalance(CardEntity.INITIAL_BALANCE);
        when(cardProvider.findByCardNumberForUpdate(CardTestFixtures.CARD_NUMBER)).thenReturn(Optional.of(card));

        CardEntity result = cardExistsUseCase.execute(CardTestFixtures.CARD_NUMBER);

        assertThat(result).isSameAs(card);
    }

    @Test
    void shouldThrowWhenCardDoesNotExist() {
        when(cardProvider.findByCardNumberForUpdate(CardTestFixtures.CARD_NUMBER)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cardExistsUseCase.execute(CardTestFixtures.CARD_NUMBER))
                .isInstanceOf(AuthorizationException.class)
                .extracting("errorCode")
                .isEqualTo(BusinessErrorCodeEnum.CARD_NOT_FOUND);
    }
}
