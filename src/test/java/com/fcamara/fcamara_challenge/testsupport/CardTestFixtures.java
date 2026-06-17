package com.fcamara.fcamara_challenge.testsupport;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class CardTestFixtures {

    public static final String CARD_NUMBER = "6549873025634501";
    public static final String PASSWORD = "1234";

    private CardTestFixtures() {
    }

    public static CardEntity cardWithBalance(BigDecimal balance) {
        return CardEntity.restore(
                1L,
                CARD_NUMBER,
                PASSWORD,
                balance,
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 10, 0)
        );
    }
}
