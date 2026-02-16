package com.desafio.cupons.service;

import com.desafio.cupons.domain.dto.CouponDto;
import com.desafio.cupons.domain.model.Coupon;
import com.desafio.cupons.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCouponService {

    private final CouponRepository couponRepository;

    public CreateCouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon save(CouponDto couponDto) {
        var coupon = Coupon.create(couponDto.code(), couponDto.description(), couponDto.discountValue(), couponDto.expirationDate(), couponDto.published());
        return couponRepository.save(coupon);
    }

}
