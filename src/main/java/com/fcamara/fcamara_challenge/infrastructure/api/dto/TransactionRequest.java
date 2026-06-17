package com.fcamara.fcamara_challenge.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados para autorização de transação")
public record TransactionRequest(
        @Schema(description = "Número do cartão", example = "6549873025634501")
        @JsonProperty("numeroCartao") String cardNumber,

        @Schema(description = "Senha do cartão", example = "1234")
        @JsonProperty("senhaCartao") String cardPassword,

        @Schema(description = "Valor da transação", example = "10.00")
        @JsonProperty("valor") BigDecimal amount
) {
}
