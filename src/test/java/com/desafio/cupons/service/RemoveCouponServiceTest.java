package com.desafio.cupons.service;

import com.desafio.cupons.domain.enums.CouponStates;
import com.desafio.cupons.domain.exception.RemoveCouponDeletedException;
import com.desafio.cupons.domain.model.Coupon;
import com.desafio.cupons.factory.CouponFactory;
import com.desafio.cupons.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveCouponServiceTest {

    @InjectMocks
    private RemoveCouponService service;

    @Mock
    private CouponRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveExecutarDeleteComSucesso() {
        var couponDeleted = CouponFactory.createCoupon();
        couponDeleted.delete();
        var coupon = CouponFactory.createCoupon();
        when(repository.save(any(Coupon.class))).thenReturn(couponDeleted);

        assertDoesNotThrow(() -> service.delete(coupon));
        verify(repository, times(1)).save(any(Coupon.class));
        assertEquals(CouponStates.DELETED, couponDeleted.getStatus());
    }

    @Test
    void deveExecutarDeleteComRemoveCouponDeletedException() {
        var couponDeleted = CouponFactory.createCoupon();
        couponDeleted.delete();

        assertThrows(RemoveCouponDeletedException.class, () -> service.delete(couponDeleted));
        verify(repository, never()).delete(any(Coupon.class));
    }

}