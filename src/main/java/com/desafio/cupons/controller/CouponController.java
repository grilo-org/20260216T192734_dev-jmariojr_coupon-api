package com.desafio.cupons.controller;

import com.desafio.cupons.domain.dto.CouponDto;
import com.desafio.cupons.domain.dto.ResponseDto;
import com.desafio.cupons.domain.exception.NotFoundCouponException;
import com.desafio.cupons.domain.exception.RemoveCouponDeletedException;
import com.desafio.cupons.domain.exception.ValidateCouponException;
import com.desafio.cupons.domain.model.Coupon;
import com.desafio.cupons.service.CreateCouponService;
import com.desafio.cupons.service.FindCouponService;
import com.desafio.cupons.service.RemoveCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/coupon")
@Tag(name = "Coupon API", description = "API para gerenciamento de cupons de desconto")
public class CouponController {

    private final CreateCouponService createCouponService;
    private final FindCouponService findCouponService;
    private final RemoveCouponService removeCouponService;

    public CouponController(CreateCouponService createCouponService, FindCouponService findCouponService, RemoveCouponService removeCouponService) {
        this.createCouponService = createCouponService;
        this.findCouponService = findCouponService;
        this.removeCouponService = removeCouponService;
    }

    private ResponseDto<Coupon> mountResponse(String message, Coupon coupon) {
        return new ResponseDto<>(
                        message,
                        coupon
                );
    }

    @PostMapping
    @Operation(summary = "Criar um cupom de desconto", description = "Cria um novo cupom de desconto com base nos dados de entrada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cupom criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    public ResponseEntity<ResponseDto<Coupon>> save(@RequestBody CouponDto couponDto) {
        try {
            var coupon = createCouponService.save(couponDto);
            return ResponseEntity.created(
                    URI.create(String.format("/api/coupon/%s", coupon.getId().toString()))
            ).body(mountResponse("Cupom criado com sucesso.", coupon));
        } catch (ValidateCouponException ex) {
            return ResponseEntity.badRequest().body(mountResponse(ex.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Busca um cupom de desconto pelo seu ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do cupom inválido"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<ResponseDto<Coupon>> find(
            @Parameter(description = "ID do cupom a ser buscado", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
            @PathVariable String id) {
        try {
            var coupon = findCouponService.findById(id);
            return ResponseEntity.ok(mountResponse("Cupom encontrado com sucesso.", coupon));
        }
        catch (NotFoundCouponException ex) {
            return ResponseEntity.notFound().build();
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(mountResponse(String.format("Parâmetro inválido. ID=%s", id), null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar por ID", description = "Deleta um cupom de desconto pelo seu ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cupom deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do cupom inválido"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado para deletar"),
            @ApiResponse(responseCode = "409", description = "O cupom informado já foi deletado")
    })
    public ResponseEntity<ResponseDto<?>> delete(
            @Parameter(description = "ID do cupom a ser deletado", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
            @PathVariable String id) {
        try {
            var value = findCouponService.findById(id);
            removeCouponService.delete(value);
            return ResponseEntity.noContent().build();
        }
        catch (NotFoundCouponException ex) {
            return ResponseEntity.notFound().build();
        }
        catch (RemoveCouponDeletedException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(mountResponse(ex.getMessage(), null));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(mountResponse(String.format("Parâmetro inválido. ID=%s", id), null));
        }
    }

}
