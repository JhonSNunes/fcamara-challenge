package com.fcamara.fcamara_challenge.infrastructure.persistence.provider;

import com.fcamara.fcamara_challenge.application.provider.CardProvider;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.infrastructure.persistence.mapper.CardMapper;
import com.fcamara.fcamara_challenge.infrastructure.persistence.repository.CardJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CardProviderImpl implements CardProvider {

    private final CardJpaRepository cardJpaRepository;
    private final CardMapper cardMapper;

    @Override
    public Optional<CardEntity> findByCardNumberForUpdate(String cardNumber) {
        return cardJpaRepository.findByCardNumberForUpdate(cardNumber)
                .map(cardMapper::toDomain);
    }

    @Override
    public Optional<CardEntity> findByCardNumber(String cardNumber) {
        return cardJpaRepository.findByCardNumber(cardNumber)
                .map(cardMapper::toDomain);
    }

    @Override
    public CardEntity save(CardEntity card) {
        return cardMapper.toDomain(cardJpaRepository.save(cardMapper.toJpa(card)));
    }
}
