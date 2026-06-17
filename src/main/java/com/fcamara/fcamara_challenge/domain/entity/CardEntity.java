package com.fcamara.fcamara_challenge.domain.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardEntity {

    public static final BigDecimal INITIAL_BALANCE = new BigDecimal("500.00");

    @EqualsAndHashCode.Include
    private Long id;

    private String cardNumber;
    private String password;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CardEntity create(String cardNumber, String password) {
        CardEntity card = new CardEntity();
        card.cardNumber = cardNumber;
        card.password = password;
        card.balance = INITIAL_BALANCE;
        card.createdAt = LocalDateTime.now();
        card.updatedAt = LocalDateTime.now();
        return card;
    }

    public static CardEntity restore(
            Long id,
            String cardNumber,
            String password,
            BigDecimal balance,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        CardEntity card = new CardEntity();
        card.id = id;
        card.cardNumber = cardNumber;
        card.password = password;
        card.balance = balance;
        card.createdAt = createdAt;
        card.updatedAt = updatedAt;
        return card;
    }

    public void debit(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount must not be null");
        balance = balance.subtract(amount);
        updatedAt = LocalDateTime.now();
    }
}
