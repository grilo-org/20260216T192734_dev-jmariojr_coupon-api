package com.desafio.cupons.domain.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CouponDto(
        @Parameter(description = "Código do cupom. Deve conter 6 caracteres.", example = "PROM01", required = true)
        @NotNull(message = "O código do cupom é obrigatório.")
        String code,
        @Parameter(description = "Descrição do cupom.", example = "Cupom de desconto 01", required = true)
        @NotNull(message = "A descrição do cupom é obrigatória.")
        String description,
        @Parameter(description = "Valor do desconto. Deve ser maior que 0.5", example = "0.5", required = true)
        @NotNull(message = "O valor do desconto é obrigatório.")
        Double discountValue,
        @Parameter(description = "Data de expiração do cupom. Não pode ser antes da data atual.", example = "2050-12-31T23:59:59Z", required = true)
        @NotNull(message = "A data de expiração do cupom é obrigatória.")
        Instant expirationDate,
        @Parameter(description = "Indica se o cupom está publicado ou não. Padrão: false", example = "true")
        Boolean published
) {
}
