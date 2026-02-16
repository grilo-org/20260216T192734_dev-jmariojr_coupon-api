package com.desafio.cupons.controller;

import com.desafio.cupons.domain.dto.CouponDto;
import com.desafio.cupons.domain.exception.NotFoundCouponException;
import com.desafio.cupons.domain.exception.RemoveCouponDeletedException;
import com.desafio.cupons.domain.exception.ValidateCouponException;
import com.desafio.cupons.domain.model.Coupon;
import com.desafio.cupons.factory.CouponFactory;
import com.desafio.cupons.service.CreateCouponService;
import com.desafio.cupons.service.FindCouponService;
import com.desafio.cupons.service.RemoveCouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponControllerTest {

    @Mock
    private CreateCouponService createCouponService;
    @Mock
    private FindCouponService findCouponService;
    @Mock
    private RemoveCouponService removeCouponService;

    @InjectMocks
    private CouponController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deveExecutarSaveComSucesso() {
        var couponDto = CouponFactory.createCouponDto();
        var coupon = CouponFactory.createCoupon();
        when(createCouponService.save(any(CouponDto.class))).thenReturn(coupon);

        var response = controller.save(couponDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveExecutarSaveComValidateCouponException() {
        when(createCouponService.save(any(CouponDto.class))).thenThrow(ValidateCouponException.class);

        var couponDto = CouponFactory.createCouponDto("DESCONTO-ERRO", null, null, null, true);
        var response = controller.save(couponDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveExecutarFindComSucesso() {
        var coupon = CouponFactory.createCoupon();
        when(findCouponService.findById(anyString())).thenReturn(coupon);
        var response = controller.find(anyString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getData(), coupon);
    }

    @Test
    void deveExecutarFindComNotFoundCouponException() {
        when(findCouponService.findById(anyString())).thenThrow(NotFoundCouponException.class);
        var response = controller.find(anyString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deveExecutarFindComIllegalArgumentException() {
        when(findCouponService.findById(anyString())).thenThrow(IllegalArgumentException.class);
        var response = controller.find(anyString());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveExecutarDeleteComSucesso() {
        var coupon = CouponFactory.createCoupon();
        when(findCouponService.findById(anyString())).thenReturn(coupon);
        doNothing().when(removeCouponService).delete(coupon);

        var response = controller.delete(anyString());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deveExecutarDeleteComNotFoundCouponException() {
        when(findCouponService.findById(anyString())).thenThrow(NotFoundCouponException.class);

        var response = controller.delete(anyString());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(removeCouponService, never()).delete(any(Coupon.class));

    }

    @Test
    void deveExecutarDeleteComRemoveCouponDeletedException() {
        var couponJaDeletado = CouponFactory.createCoupon();
        couponJaDeletado.delete();

        when(findCouponService.findById(anyString())).thenReturn(couponJaDeletado);
        doThrow(RemoveCouponDeletedException.class).when(removeCouponService).delete(any(Coupon.class));

        var response = controller.delete(anyString());
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(removeCouponService, times(1)).delete(any(Coupon.class));
    }

    @Test
    void deveExecutarDeleteComIllegalArgumentException() {
        when(findCouponService.findById(anyString())).thenThrow(IllegalArgumentException.class);

        var response = controller.delete(anyString());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(removeCouponService, never()).delete(any(Coupon.class));
    }

}