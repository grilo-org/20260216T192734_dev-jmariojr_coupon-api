package com.desafio.cupons.domain.enums;

import lombok.Getter;

@Getter
public enum CouponStates {

    ACTIVE("ativo"),
    INACTIVE("inativo"),
    DELETED("deletado");

    private final String status;

    CouponStates(String status) {
        this.status = status;
    }

}
