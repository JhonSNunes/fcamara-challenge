package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.CardNotFoundException;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCardBalanceUseCaseTest {

    @Mock
    private CardProvider cardProvider;

    @InjectMocks
    private GetCardBalanceUseCase getCardBalanceUseCase;

    @Test
    void shouldReturnCardBalance() {
        BigDecimal balance = new BigDecimal("495.15");
        when(cardProvider.findByCardNumber(CardTestFixtures.CARD_NUMBER))
                .thenReturn(Optional.of(CardTestFixtures.cardWithBalance(balance)));

        BigDecimal result = getCardBalanceUseCase.execute(CardTestFixtures.CARD_NUMBER);

        assertThat(result).isEqualByComparingTo(balance);
    }

    @Test
    void shouldThrowWhenCardNotFound() {
        when(cardProvider.findByCardNumber(CardTestFixtures.CARD_NUMBER)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getCardBalanceUseCase.execute(CardTestFixtures.CARD_NUMBER))
                .isInstanceOf(CardNotFoundException.class);
    }
}
