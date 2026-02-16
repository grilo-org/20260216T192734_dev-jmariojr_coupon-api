package com.desafio.cupons.domain.exception;

import com.desafio.cupons.domain.enums.ValidadeDomain;

public class ValidateCouponException extends RuntimeException {
    public ValidateCouponException(ValidadeDomain value) {
        super(value.getMessage());
    }
}
