package com.fcamara.fcamara_challenge.infrastructure.persistence.mapper;

import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.infrastructure.persistence.entity.CardJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public CardEntity toDomain(CardJpaEntity entity) {
        return CardEntity.restore(
                entity.getId(),
                entity.getCardNumber(),
                entity.getPassword(),
                entity.getBalance(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CardJpaEntity toJpa(CardEntity card) {
        return CardJpaEntity.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .password(card.getPassword())
                .balance(card.getBalance())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .build();
    }
}
