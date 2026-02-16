package com.desafio.cupons.service;

import com.desafio.cupons.domain.exception.NotFoundCouponException;
import com.desafio.cupons.domain.model.Coupon;
import com.desafio.cupons.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindCouponService {

    private final CouponRepository couponRepository;

    public FindCouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon findById(String id) {
        UUID uuid = UUID.fromString(id);
        return couponRepository.findById(uuid).orElseThrow(() -> new NotFoundCouponException(id));
    }

}
