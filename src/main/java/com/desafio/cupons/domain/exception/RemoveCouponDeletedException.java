package com.desafio.cupons.domain.exception;

import java.util.UUID;

public class RemoveCouponDeletedException extends RuntimeException {

    public RemoveCouponDeletedException(UUID id) {
        super(String.format("O cupom %s já foi deletado.", id.toString()));
    }
}
