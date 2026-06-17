package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DebitBalanceUseCaseTest {

    @Mock
    private CardProvider cardProvider;

    @InjectMocks
    private DebitBalanceUseCase debitBalanceUseCase;

    @Test
    void shouldDebitBalanceAndPersistCard() {
        CardEntity card = CardTestFixtures.cardWithBalance(CardEntity.INITIAL_BALANCE);
        BigDecimal amount = new BigDecimal("10.00");
        when(cardProvider.save(card)).thenReturn(card);

        debitBalanceUseCase.execute(card, amount);

        assertThat(card.getBalance()).isEqualByComparingTo(new BigDecimal("490.00"));

        ArgumentCaptor<CardEntity> cardCaptor = ArgumentCaptor.forClass(CardEntity.class);
        verify(cardProvider).save(cardCaptor.capture());
        assertThat(cardCaptor.getValue().getBalance()).isEqualByComparingTo(new BigDecimal("490.00"));
    }
}
