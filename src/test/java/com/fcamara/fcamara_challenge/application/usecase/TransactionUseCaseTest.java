package com.fcamara.fcamara_challenge.application.usecase;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.domain.exception.AuthorizationException;
import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionUseCaseTest {

    @Mock
    private CardExistsUseCase cardExistsUseCase;

    @Mock
    private PasswordMatchesUseCase passwordMatchesUseCase;

    @Mock
    private EnoughBalanceUseCase enoughBalanceUseCase;

    @Mock
    private DebitBalanceUseCase debitBalanceUseCase;

    @InjectMocks
    private TransactionUseCase transactionUseCase;

    @Test
    void shouldExecuteAuthorizationRulesInOrder() {
        CardEntity card = CardTestFixtures.cardWithBalance(CardEntity.INITIAL_BALANCE);
        BigDecimal amount = new BigDecimal("10.00");

        when(cardExistsUseCase.execute(CardTestFixtures.CARD_NUMBER)).thenReturn(card);

        transactionUseCase.execute(CardTestFixtures.CARD_NUMBER, CardTestFixtures.PASSWORD, amount);

        InOrder inOrder = inOrder(
                cardExistsUseCase,
                passwordMatchesUseCase,
                enoughBalanceUseCase,
                debitBalanceUseCase
        );

        inOrder.verify(cardExistsUseCase).execute(CardTestFixtures.CARD_NUMBER);
        inOrder.verify(passwordMatchesUseCase).execute(card, CardTestFixtures.PASSWORD);
        inOrder.verify(enoughBalanceUseCase).execute(card, amount);
        inOrder.verify(debitBalanceUseCase).execute(card, amount);
        verifyNoMoreInteractions(cardExistsUseCase, passwordMatchesUseCase, enoughBalanceUseCase, debitBalanceUseCase);
    }

    @Test
    void shouldStopWhenCardDoesNotExist() {
        when(cardExistsUseCase.execute(CardTestFixtures.CARD_NUMBER))
                .thenThrow(new AuthorizationException(BusinessErrorCodeEnum.CARD_NOT_FOUND));

        assertThatThrownBy(() -> transactionUseCase.execute(
                CardTestFixtures.CARD_NUMBER,
                CardTestFixtures.PASSWORD,
                new BigDecimal("10.00")
        )).isInstanceOf(AuthorizationException.class);

        verifyNoMoreInteractions(passwordMatchesUseCase, enoughBalanceUseCase, debitBalanceUseCase);
    }
}
