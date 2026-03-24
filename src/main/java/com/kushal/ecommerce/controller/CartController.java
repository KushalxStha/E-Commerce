package com.kushal.ecommerce.controller;

import com.kushal.ecommerce.dto.ApiResponseDTO;
import com.kushal.ecommerce.dto.CartResponseDTO;
import com.kushal.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<CartResponseDTO>> getCartById(@PathVariable Long id){
        CartResponseDTO response = cartService.getCartById(id);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> clearCart(@PathVariable Long id){
        cartService.clearCart(id);
        return ResponseEntity.ok(new ApiResponseDTO<>("Cart cleared!", null));
    }

    @GetMapping("/{id}/total-price")
    public ResponseEntity<ApiResponseDTO<BigDecimal>> getTotalPrice(@PathVariable Long id){
        BigDecimal totalPrice = cartService.getTotalPrice(id);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", totalPrice));
    }
}
