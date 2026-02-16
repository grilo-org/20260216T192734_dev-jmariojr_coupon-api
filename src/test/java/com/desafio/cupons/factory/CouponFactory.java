package com.desafio.cupons.factory;

import com.desafio.cupons.domain.dto.CouponDto;
import com.desafio.cupons.domain.dto.ResponseDto;
import com.desafio.cupons.domain.model.Coupon;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class CouponFactory {

    public static Coupon createCoupon() {
        return Coupon.create("PROM01", "Promoção desconto 01", 0.7, Instant.now().plusSeconds(3600L), false);
    }

    public static ResponseDto<Coupon> createResponseCoupon(HttpStatus status) {
        ResponseDto<Coupon> response = null;
        switch (status) {
            case OK:
                response = new ResponseDto<>("Executado com sucesso.", createCoupon());
                break;
            case CREATED:
                response = new ResponseDto<>("Criado com sucesso.", null);
                break;
            case BAD_REQUEST:
                response = new ResponseDto<>("Erro parâmetro.", null);
                break;
            case CONFLICT:
                response = new ResponseDto<>("Cupom já deletado.", null);
                break;
            default:
                break;
        }

        return response;
    }

    public static CouponDto createCouponDto() {
        return createCouponDto("DES_123", "Desconto 123", 0.7, Instant.now().plusSeconds(3600L), false);
    }

    public static CouponDto createCouponDto(String code, String description, Double discountValue, Instant expirationDate, boolean published) {
        return new CouponDto(code, description, discountValue, expirationDate, published);
    }
}
