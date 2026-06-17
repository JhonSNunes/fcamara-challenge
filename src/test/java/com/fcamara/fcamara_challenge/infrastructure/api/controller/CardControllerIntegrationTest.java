package com.fcamara.fcamara_challenge.infrastructure.api.controller;

import com.fcamara.fcamara_challenge.infrastructure.persistence.repository.CardJpaRepository;
import com.fcamara.fcamara_challenge.testsupport.CardTestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardJpaRepository cardJpaRepository;

    @BeforeEach
    void setUp() {
        cardJpaRepository.deleteAll();
    }

    @Test
    void shouldCreateCard() throws Exception {
        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroCartao": "%s",
                                  "senha": "%s"
                                }
                                """.formatted(CardTestFixtures.CARD_NUMBER, CardTestFixtures.PASSWORD)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCartao").value(CardTestFixtures.CARD_NUMBER))
                .andExpect(jsonPath("$.senha").value(CardTestFixtures.PASSWORD));
    }

    @Test
    void shouldReturnUnprocessableEntityWhenCardAlreadyExists() throws Exception {
        String body = """
                {
                  "numeroCartao": "%s",
                  "senha": "%s"
                }
                """.formatted(CardTestFixtures.CARD_NUMBER, CardTestFixtures.PASSWORD);

        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.numeroCartao").value(CardTestFixtures.CARD_NUMBER))
                .andExpect(jsonPath("$.senha").value(CardTestFixtures.PASSWORD));
    }

    @Test
    void shouldReturnBalanceForExistingCard() throws Exception {
        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroCartao": "%s",
                                  "senha": "%s"
                                }
                                """.formatted(CardTestFixtures.CARD_NUMBER, CardTestFixtures.PASSWORD)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/cartoes/{numeroCartao}", CardTestFixtures.CARD_NUMBER))
                .andExpect(status().isOk())
                .andExpect(content().json("500.00"));
    }

    @Test
    void shouldReturnNotFoundWhenCardDoesNotExist() throws Exception {
        mockMvc.perform(get("/cartoes/{numeroCartao}", CardTestFixtures.CARD_NUMBER))
                .andExpect(status().isNotFound());
    }
}
