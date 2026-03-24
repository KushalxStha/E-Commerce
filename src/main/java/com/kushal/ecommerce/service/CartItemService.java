package com.kushal.ecommerce.service;

import com.kushal.ecommerce.exception.ResourceNotFoundException;
import com.kushal.ecommerce.model.Cart;
import com.kushal.ecommerce.model.CartItem;
import com.kushal.ecommerce.model.Product;
import com.kushal.ecommerce.repo.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductService productService;

    public void addItemToCart(Long cartId, Long productId, int quantity){
        Cart cart = cartService.getCartEntityById(cartId);
        Product product = productService.getProductEntityById(productId);

        Optional<CartItem> existingItem = cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst();

        if(existingItem.isPresent()){
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(product, quantity);

            cart.addItem(newItem);
        }
        cartRepository.save(cart);
    }

    public void removeItemFromCart(Long cartId, Long productId){
        Cart cart = cartService.getCartEntityById(cartId);
        CartItem itemToRemove = cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Item not found in cart."));

        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    public void updateItemQuantity(Long cartId, Long productId, int quantity){
        Cart cart = cartService.getCartEntityById(cartId);
        CartItem itemToUpdate = cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Item not found in cart."));

        itemToUpdate.setQuantity(quantity);
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }
}
