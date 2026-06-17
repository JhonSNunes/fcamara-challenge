package com.fcamara.fcamara_challenge.infrastructure.api.controller;

import com.fcamara.fcamara_challenge.application.usecase.TransactionUseCase;
import com.fcamara.fcamara_challenge.domain.exception.BusinessErrorCodeEnum;
import com.fcamara.fcamara_challenge.infrastructure.api.HttpStatusCode;
import com.fcamara.fcamara_challenge.infrastructure.api.dto.TransactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Transações", description = "Autorização de transações com cartão")
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionUseCase transactionUseCase;

    @Operation(
            summary = "Autorizar transação",
            description = "Valida as regras de autorização e debita o saldo do cartão em caso de aprovação"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = HttpStatusCode.CREATED,
                    description = "Transação autorizada com sucesso",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(type = "string", example = "OK")
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.UNPROCESSABLE_ENTITY,
                    description = "Transação não autorizada",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "Saldo insuficiente",
                                            value = BusinessErrorCodeEnum.Codes.INSUFFICIENT_BALANCE
                                    ),
                                    @ExampleObject(
                                            name = "Senha inválida",
                                            value = BusinessErrorCodeEnum.Codes.INVALID_PASSWORD
                                    ),
                                    @ExampleObject(
                                            name = "Cartão inexistente",
                                            value = BusinessErrorCodeEnum.Codes.CARD_NOT_FOUND
                                    )
                            }
                    )
            )
    })
    @PostMapping("/transacoes")
    public ResponseEntity<String> authorize(@RequestBody TransactionRequest request) {
        transactionUseCase.execute(
                request.cardNumber(),
                request.cardPassword(),
                request.amount()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
}
