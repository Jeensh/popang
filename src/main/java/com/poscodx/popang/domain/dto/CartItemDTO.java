package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.Cart;
import com.poscodx.popang.domain.CartItem;
import com.poscodx.popang.domain.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long count;
    private Long cartId;
    private Long productId;
    private ProductDTO product;

    public void setDTOByEntity(CartItem cartItem){
        id = cartItem.getId();
        count = cartItem.getCount();
        cartId = cartItem.getCart().getId();
        productId = cartItem.getProduct().getId();
    }

}
