package com.kushal.ecommerce.controller;

import com.kushal.ecommerce.dto.ApiResponseDTO;
import com.kushal.ecommerce.service.CartItemService;
import com.kushal.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${api.prefix}/cart-items")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Void>> addItemToCart(@RequestParam(required = false) Long cartId, @RequestParam Long productId, @RequestParam int quantity){
        if(cartId == null){
            cartId = cartService.initializeNewCart();
        }
        cartItemService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.ok(new ApiResponseDTO<>("Item added to cart!", null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseDTO<Void>> removeItemFromCart(@RequestParam Long cartId, @RequestParam Long productId){
        cartItemService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Item removed from cart!", null));
    }

    @PutMapping
    public ResponseEntity<ApiResponseDTO<Void>> updateQuantity(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity){
        cartItemService.updateItemQuantity(cartId, productId, quantity);
        return ResponseEntity.ok(new ApiResponseDTO<>("Quantity updated!", null));
    }
}
