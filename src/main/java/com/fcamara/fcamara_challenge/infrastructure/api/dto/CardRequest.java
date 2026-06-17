package com.fcamara.fcamara_challenge.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criação de cartão")
public record CardRequest(
        @Schema(description = "Número do cartão", example = "6549873025634501")
        @JsonProperty("numeroCartao") String cardNumber,

        @Schema(description = "Senha do cartão", example = "1234")
        @JsonProperty("senha") String password
) {
}
