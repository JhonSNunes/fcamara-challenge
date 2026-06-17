package com.fcamara.fcamara_challenge.infrastructure.api.controller;

import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import com.fcamara.fcamara_challenge.infrastructure.persistence.repository.CardJpaRepository;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardJpaRepository cardJpaRepository;

    @BeforeEach
    void setUp() {
        cardJpaRepository.deleteAll();
    }

    @Test
    void shouldAuthorizeTransactionAndDebitBalance() throws Exception {
        createCard();

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionBody("10.00")))
                .andExpect(status().isCreated())
                .andExpect(content().string("OK"));

        mockMvc.perform(get("/cartoes/{numeroCartao}", CardTestFixtures.CARD_NUMBER))
                .andExpect(status().isOk())
                .andExpect(content().json("490.00"));
    }

    @Test
    void shouldReturnInsufficientBalanceWhenDebitExceedsAvailableAmount() throws Exception {
        createCard();

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionBody("500.01")))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(BusinessErrorCodeEnum.Codes.INSUFFICIENT_BALANCE));
    }

    @Test
    void shouldReturnInvalidPasswordWhenPasswordDoesNotMatch() throws Exception {
        createCard();

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroCartao": "%s",
                                  "senhaCartao": "9999",
                                  "valor": 10.00
                                }
                                """.formatted(CardTestFixtures.CARD_NUMBER)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(BusinessErrorCodeEnum.Codes.INVALID_PASSWORD));
    }

    @Test
    void shouldReturnCardNotFoundWhenCardDoesNotExist() throws Exception {
        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionBody("10.00")))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(BusinessErrorCodeEnum.Codes.CARD_NOT_FOUND));
    }

    @Test
    void shouldHandleConcurrentTransactionsWithPessimisticLock() throws Exception {
        createCard();

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionBody("490.00")))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/cartoes/{numeroCartao}", CardTestFixtures.CARD_NUMBER))
                .andExpect(status().isOk())
                .andExpect(content().json("10.00"));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startLatch = new CountDownLatch(1);

        try {
            List<Callable<MvcResult>> tasks = new ArrayList<>();
            for (int index = 0; index < 2; index++) {
                tasks.add(() -> {
                    startLatch.await();
                    return mockMvc.perform(post("/transacoes")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(transactionBody("10.00")))
                            .andReturn();
                });
            }

            List<Future<MvcResult>> futures = tasks.stream()
                    .map(executor::submit)
                    .toList();

            startLatch.countDown();

            List<Integer> statuses = futures.stream()
                    .map(future -> {
                        try {
                            return future.get().getResponse().getStatus();
                        } catch (Exception exception) {
                            throw new IllegalStateException(exception);
                        }
                    })
                    .toList();

            assertThat(statuses).containsExactlyInAnyOrder(201, 422);

            mockMvc.perform(get("/cartoes/{numeroCartao}", CardTestFixtures.CARD_NUMBER))
                    .andExpect(status().isOk())
                    .andExpect(content().json("0.00"));
        } finally {
            executor.shutdownNow();
        }
    }

    private void createCard() throws Exception {
        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroCartao": "%s",
                                  "senha": "%s"
                                }
                                """.formatted(CardTestFixtures.CARD_NUMBER, CardTestFixtures.PASSWORD)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCartao").value(CardTestFixtures.CARD_NUMBER));
    }

    private String transactionBody(String amount) {
        return """
                {
                  "numeroCartao": "%s",
                  "senhaCartao": "%s",
                  "valor": %s
                }
                """.formatted(CardTestFixtures.CARD_NUMBER, CardTestFixtures.PASSWORD, amount);
    }
}
