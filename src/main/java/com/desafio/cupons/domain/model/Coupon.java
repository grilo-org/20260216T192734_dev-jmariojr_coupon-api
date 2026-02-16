package com.desafio.cupons.domain.model;

import com.desafio.cupons.domain.enums.CouponStates;
import com.desafio.cupons.domain.enums.ValidadeDomain;
import com.desafio.cupons.domain.exception.RemoveCouponDeletedException;
import com.desafio.cupons.domain.exception.ValidateCouponException;
import jakarta.persistence.Id;
import lombok.Getter;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
public class Coupon {

    @Id
    private UUID id;
    private String code;
    private String description;
    private Double discountValue;
    private Instant expirationDate;

    private Boolean published;
    private Boolean redeemed;

    @Enumerated(EnumType.STRING)
    private CouponStates status;

    protected Coupon() {
    }

    public static Coupon create(String code, String description, Double discountValue, Instant expirationDate, Boolean published) {
        validateFieds(code, description, discountValue, expirationDate);
        validateCode(code);
        validateDiscountValeu(discountValue);
        validateExpirationDate(expirationDate);

        if(published == null) {
            published = false;
        }

        return new Coupon(
                UUID.randomUUID(),
                extractCodeValue(code),
                description,
                discountValue,
                expirationDate,
                published,
                CouponStates.ACTIVE,
                false
        );
    }

    public void delete() {
        if (this.status == CouponStates.DELETED)
            throw new RemoveCouponDeletedException(this.id);

        this.status = CouponStates.DELETED;
    }

    private static void validateFieds(
            String code,
            String description,
            Double discountValue,
            Instant expirationDate
    ) {
        if (code == null || description == null || discountValue == null || expirationDate == null) {
            throw new ValidateCouponException(ValidadeDomain.ALL_FIELDS_REQUIRED);
        }
    }

    private static void validateCode(String code) {
        String value = extractCodeValue(code);
        if (value.length() != 6) {
            throw new ValidateCouponException(ValidadeDomain.CODE_LENGTH_INVALID);
        }
    }

    private static void validateDiscountValeu(Double discountValue) {
        var discountValueBigDecimal = BigDecimal.valueOf(discountValue);
        if (discountValueBigDecimal.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new ValidateCouponException(ValidadeDomain.DISCOUNT_VALUE_INVALID);
        }
    }

    private static void validateExpirationDate(Instant expirationDate) {
        if (expirationDate.isBefore(Instant.now())) {
            throw new ValidateCouponException(ValidadeDomain.EXPIRATION_DATE_INVALID);
        }
    }

    private static String extractCodeValue(String code) {
        return code.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }

    private Coupon(
            UUID id,
            String code,
            String description,
            Double discountValue,
            Instant expirationDate,
            Boolean published,
            CouponStates status,
            Boolean redeemed
    ) {
        this.id = id;
        this.code = extractCodeValue(code);
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
        this.status = status;
        this.redeemed = redeemed;
    }
}
