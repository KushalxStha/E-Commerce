package com.kushal.ecommerce.service;

import com.kushal.ecommerce.exception.ResourceNotFoundException;
import com.kushal.ecommerce.model.Cart;
import com.kushal.ecommerce.repo.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    @Transactional(readOnly = true)
    public Cart getCart(Long id){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found with id: " + id));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);

        return cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(Long id){
        Cart cart = getCart(id);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalPrice(Long id){
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }
}
