package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.Orders;
import com.poscodx.popang.domain.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private Long count;
    private Long totalPrice;
    private Long orderId;
    private Long productId;
    private ProductDTO product;

    public void setDTOByEntity(OrderItem orderItem){
        id = orderItem.getId();
        count = orderItem.getCount();
        totalPrice = orderItem.getTotalPrice();
        orderId = orderItem.getOrder().getId();
        productId = orderItem.getProduct().getId();
    }
}
