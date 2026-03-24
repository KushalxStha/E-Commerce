package com.kushal.ecommerce.service;

import com.kushal.ecommerce.dto.CartResponseDTO;
import com.kushal.ecommerce.exception.ResourceNotFoundException;
import com.kushal.ecommerce.mapper.CartMapper;
import com.kushal.ecommerce.model.Cart;
import com.kushal.ecommerce.repo.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public Cart getCartEntityById(Long id){
         return cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found with id: " + id));
    }

    public CartResponseDTO getCartById(Long id){
        return cartRepository.findById(id)
                .map(cartMapper::mapToCartResponseDTO)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found with id: " + id));
    }

    @Transactional
    public void clearCart(Long id){
        Cart cart = getCartEntityById(id);
        cart.clearCart();
        cartRepository.save(cart);  // Optional, since Transaction is used.
        // Necessary only when a new Entity is created
    }

    public BigDecimal getTotalPrice(Long id){
        return getCartEntityById(id).getTotalAmount();
    }

    @Transactional
    public Long initializeNewCart(){
        return cartRepository.save(new Cart()).getId();
    }
}
