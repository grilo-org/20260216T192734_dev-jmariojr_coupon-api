package com.desafio.cupons.domain.enums;

import lombok.Getter;

@Getter
public enum ValidadeDomain {

    ALL_FIELDS_REQUIRED("Todos os campos são obrigatórios"),
    CODE_LENGTH_INVALID("O campo 'code' deve conter 6 caracteres"),
    DISCOUNT_VALUE_INVALID("O campo 'discountValue' deve ser maior ou igual a 0.5"),
    EXPIRATION_DATE_INVALID("O campo 'expirationDate' não pode ser antes da data atual");

    private final String message;

    ValidadeDomain(String message) {
        this.message = message;
    }
}
