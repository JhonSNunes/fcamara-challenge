package com.fcamara.fcamara_challenge.infrastructure.persistence.provider;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.infrastructure.persistence.entity.CardJpaEntity;
import com.fcamara.fcamara_challenge.infrastructure.persistence.mapper.CardMapper;
import com.fcamara.fcamara_challenge.infrastructure.persistence.repository.CardJpaRepository;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardProviderImplTest {

    @Mock
    private CardJpaRepository cardJpaRepository;

    private final CardMapper cardMapper = new CardMapper();

    private CardProviderImpl cardProvider;

    @BeforeEach
    void setUp() {
        cardProvider = new CardProviderImpl(cardJpaRepository, cardMapper);
    }

    @Test
    void shouldFindCardByNumberForUpdate() {
        CardJpaEntity jpaEntity = jpaEntity();
        when(cardJpaRepository.findByCardNumberForUpdate(CardTestFixtures.CARD_NUMBER))
                .thenReturn(Optional.of(jpaEntity));

        Optional<CardEntity> result = cardProvider.findByCardNumberForUpdate(CardTestFixtures.CARD_NUMBER);

        assertThat(result).isPresent();
        assertThat(result.get().getCardNumber()).isEqualTo(CardTestFixtures.CARD_NUMBER);
        assertThat(result.get().getBalance()).isEqualByComparingTo(CardEntity.INITIAL_BALANCE);
        verify(cardJpaRepository).findByCardNumberForUpdate(CardTestFixtures.CARD_NUMBER);
    }

    @Test
    void shouldFindCardByNumber() {
        CardJpaEntity jpaEntity = jpaEntity();
        when(cardJpaRepository.findByCardNumber(CardTestFixtures.CARD_NUMBER))
                .thenReturn(Optional.of(jpaEntity));

        Optional<CardEntity> result = cardProvider.findByCardNumber(CardTestFixtures.CARD_NUMBER);

        assertThat(result).isPresent();
        assertThat(result.get().getPassword()).isEqualTo(CardTestFixtures.PASSWORD);
        verify(cardJpaRepository).findByCardNumber(CardTestFixtures.CARD_NUMBER);
    }

    @Test
    void shouldSaveCard() {
        CardEntity card = CardTestFixtures.cardWithBalance(new BigDecimal("490.00"));
        CardJpaEntity savedJpaEntity = jpaEntity();
        when(cardJpaRepository.save(any(CardJpaEntity.class))).thenReturn(savedJpaEntity);

        CardEntity result = cardProvider.save(card);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCardNumber()).isEqualTo(CardTestFixtures.CARD_NUMBER);
        verify(cardJpaRepository).save(any(CardJpaEntity.class));
    }

    private CardJpaEntity jpaEntity() {
        return CardJpaEntity.builder()
                .id(1L)
                .cardNumber(CardTestFixtures.CARD_NUMBER)
                .password(CardTestFixtures.PASSWORD)
                .balance(CardEntity.INITIAL_BALANCE)
                .createdAt(LocalDateTime.of(2026, 1, 1, 10, 0))
                .updatedAt(LocalDateTime.of(2026, 1, 1, 10, 0))
                .build();
    }
}
