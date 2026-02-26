package com.kushal.ecommerce.mapper;

import com.kushal.ecommerce.dto.CartResponseDTO;
import com.kushal.ecommerce.model.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    public CartResponseDTO mapToCartResponseDTO(Cart cart){
        return new CartResponseDTO(
                cart.getId(),
                cart.getTotalAmount()
        );
    }
}
