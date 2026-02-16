package com.desafio.cupons.service;

import com.desafio.cupons.domain.model.Coupon;
import com.desafio.cupons.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class RemoveCouponService {

    private final CouponRepository couponRepository;

    public RemoveCouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void delete(Coupon coupon) {
        coupon.delete();
        couponRepository.save(coupon);
    }
}
