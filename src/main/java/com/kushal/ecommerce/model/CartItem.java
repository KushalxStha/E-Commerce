package com.kushal.ecommerce.model;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public void calculateTotalPrice(){
        if (unitPrice != null) {
            totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        } else {
            totalPrice = BigDecimal.ZERO;
        }
    }

    public BigDecimal getTotalPrice(){
        return totalPrice != null ? totalPrice : BigDecimal.ZERO;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
        calculateTotalPrice();
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.unitPrice = product.getPrice();
        setQuantity(quantity);
    }
}
