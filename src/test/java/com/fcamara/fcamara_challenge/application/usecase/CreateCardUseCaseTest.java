package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.CardAlreadyExistsException;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCardUseCaseTest {

    @Mock
    private CardProvider cardProvider;

    @InjectMocks
    private CreateCardUseCase createCardUseCase;

    @Test
    void shouldCreateCardWithInitialBalance() {
        when(cardProvider.findByCardNumber(CardTestFixtures.CARD_NUMBER)).thenReturn(Optional.empty());
        when(cardProvider.save(any(CardEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CardEntity card = createCardUseCase.execute(CardTestFixtures.CARD_NUMBER, CardTestFixtures.PASSWORD);

        ArgumentCaptor<CardEntity> cardCaptor = ArgumentCaptor.forClass(CardEntity.class);
        verify(cardProvider).save(cardCaptor.capture());

        assertThat(card.getCardNumber()).isEqualTo(CardTestFixtures.CARD_NUMBER);
        assertThat(card.getPassword()).isEqualTo(CardTestFixtures.PASSWORD);
        assertThat(card.getBalance()).isEqualByComparingTo(CardEntity.INITIAL_BALANCE);
        assertThat(cardCaptor.getValue().getBalance()).isEqualByComparingTo(CardEntity.INITIAL_BALANCE);
    }

    @Test
    void shouldThrowWhenCardAlreadyExists() {
        when(cardProvider.findByCardNumber(CardTestFixtures.CARD_NUMBER))
                .thenReturn(Optional.of(CardTestFixtures.cardWithBalance(CardEntity.INITIAL_BALANCE)));

        assertThatThrownBy(() -> createCardUseCase.execute(CardTestFixtures.CARD_NUMBER, CardTestFixtures.PASSWORD))
                .isInstanceOf(CardAlreadyExistsException.class)
                .satisfies(exception -> {
                    CardAlreadyExistsException cardException = (CardAlreadyExistsException) exception;
                    assertThat(cardException.getCardNumber()).isEqualTo(CardTestFixtures.CARD_NUMBER);
                    assertThat(cardException.getPassword()).isEqualTo(CardTestFixtures.PASSWORD);
                });

        verify(cardProvider, never()).save(any(CardEntity.class));
    }
}
