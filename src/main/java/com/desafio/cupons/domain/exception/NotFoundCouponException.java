package com.desafio.cupons.domain.exception;

public class NotFoundCouponException extends RuntimeException {

    public NotFoundCouponException(String id) {
        super(String.format("Cupom não encontrado. Id=%s.", id));
    }
}
