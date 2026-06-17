package com.fcamara.fcamara_challenge.infrastructure.persistence.repository;

import com.fcamara.fcamara_challenge.infrastructure.persistence.entity.CardJpaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardJpaRepository extends JpaRepository<CardJpaEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CardJpaEntity c WHERE c.cardNumber = :cardNumber")
    Optional<CardJpaEntity> findByCardNumberForUpdate(@Param("cardNumber") String cardNumber);

    Optional<CardJpaEntity> findByCardNumber(String cardNumber);
}
