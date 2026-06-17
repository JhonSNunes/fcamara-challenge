package com.fcamara.fcamara_challenge.infrastructure.api.controller;

import com.fcamara.fcamara_challenge.application.usecase.CreateCardUseCase;
import com.fcamara.fcamara_challenge.application.usecase.GetCardBalanceUseCase;
import com.fcamara.fcamara_challenge.domain.entity.CardEntity;
import com.fcamara.fcamara_challenge.infrastructure.api.HttpStatusCode;
import com.fcamara.fcamara_challenge.infrastructure.api.dto.CardRequest;
import com.fcamara.fcamara_challenge.infrastructure.api.dto.CardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "Cartões", description = "Criação de cartões e consulta de saldo")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CreateCardUseCase createCardUseCase;
    private final GetCardBalanceUseCase getCardBalanceUseCase;

    @Operation(summary = "Criar cartão", description = "Cria um novo cartão com saldo inicial de R$ 500,00")
    @ApiResponses({
            @ApiResponse(
                    responseCode = HttpStatusCode.CREATED,
                    description = "Cartão criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CardResponse.class))
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY,
                    description = "Cartão já existente",
                    content = @Content(schema = @Schema(implementation = CardResponse.class))
            )
    })
    @PostMapping("/cartoes")
    public ResponseEntity<CardResponse> create(@RequestBody CardRequest request) {
        CardEntity card = createCardUseCase.execute(request.cardNumber(), request.password());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(card));
    }

    @Operation(summary = "Consultar saldo", description = "Retorna o saldo disponível do cartão")
    @ApiResponses({
            @ApiResponse(
                    responseCode = HttpStatusCode.OK,
                    description = "Saldo obtido com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(type = "number", example = "495.15")
                    )
            ),
            @ApiResponse(responseCode = HttpStatusCode.NOT_FOUND, description = "Cartão não encontrado", content = @Content)
    })
    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<BigDecimal> getBalance(
            @Parameter(description = "Número do cartão", example = "6549873025634501")
            @PathVariable("numeroCartao") String cardNumber
    ) {
        return ResponseEntity.ok(getCardBalanceUseCase.execute(cardNumber));
    }

    private CardResponse toResponse(CardEntity card) {
        return new CardResponse(card.getCardNumber(), card.getPassword());
    }
}
