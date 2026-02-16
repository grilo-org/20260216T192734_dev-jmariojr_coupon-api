package com.desafio.cupons.service;

import com.desafio.cupons.domain.exception.NotFoundCouponException;
import com.desafio.cupons.factory.CouponFactory;
import com.desafio.cupons.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindCouponServiceTest {

    @InjectMocks
    private FindCouponService service;

    @Mock
    private CouponRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveExecutarFindByIdComSucesso() {
        var coupon = CouponFactory.createCoupon();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(coupon));
        assertNotNull(service.findById(UUID.randomUUID().toString()));
    }

    @Test
    void deveExecutarFindByIdComIlegalException() {
        assertThrows(IllegalArgumentException.class, ()-> service.findById("1"));
        verify(repository, never()).findById(any(UUID.class));
    }

    @Test
    void deveExecutarFindByIdComNotFoundCouponException() {
        when(repository.findById(any(UUID.class))).thenThrow(NotFoundCouponException.class);
        assertThrows(NotFoundCouponException.class, ()-> service.findById(UUID.randomUUID().toString()));
    }
}