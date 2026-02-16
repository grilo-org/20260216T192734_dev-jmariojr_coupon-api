package com.desafio.cupons.service;

import com.desafio.cupons.domain.exception.ValidateCouponException;
import com.desafio.cupons.domain.model.Coupon;
import com.desafio.cupons.factory.CouponFactory;
import com.desafio.cupons.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCouponServiceTest {

    @InjectMocks
    private CreateCouponService service;

    @Mock
    private CouponRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveExecutarSaveComSucesso() {
        var couponSave = CouponFactory.createCoupon();
        var couponDto = CouponFactory.createCouponDto();
        when(repository.save(any(Coupon.class))).thenReturn(couponSave);

        assertDoesNotThrow(() -> service.save(couponDto));
        verify(repository, times(1)).save(any(Coupon.class));
    }

    @Test
    void deveExecutarSaveComValidateCupomExceptionCamposObrigatorios() {
        var couponDto = CouponFactory.createCouponDto("DESC1", null, null, null, true);

        assertThrows(ValidateCouponException.class, () -> service.save(couponDto));
        verify(repository, never()).save(any(Coupon.class));
    }

    @Test
    void deveExecutarSaveComValidateCupomExceptionCode() {
        var couponDto = CouponFactory.createCouponDto("DES1", "Desconto hum", 0.5, Instant.now().plusSeconds(3600L), true);

        assertThrows(ValidateCouponException.class, () -> service.save(couponDto));
        verify(repository, never()).save(any(Coupon.class));
    }

    @Test
    void deveExecutarSaveComValidateCupomExceptionDiscountValue() {
        var couponDto = CouponFactory.createCouponDto("DES123", "Desconto hum", 0.4, Instant.now().plusSeconds(3600L), true);

        assertThrows(ValidateCouponException.class, () -> service.save(couponDto));
        verify(repository, never()).save(any(Coupon.class));
    }

    @Test
    void deveExecutarSaveComValidateCupomExceptionExpirationDate() {
        var couponDto = CouponFactory.createCouponDto("DES123", "Desconto hum", 0.6, Instant.now().minusSeconds(3600L), true);

        assertThrows(ValidateCouponException.class, () -> service.save(couponDto));
        verify(repository, never()).save(any(Coupon.class));
    }
}