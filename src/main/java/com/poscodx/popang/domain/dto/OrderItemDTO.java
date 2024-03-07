package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.Orders;
import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.Refund;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderItemDTO {
    private Long id;
    private Long count;
    private Long totalPrice;
    private Long orderId;
    private Timestamp orderDate;
    private Long productId;
    private ProductDTO product;
    private RefundDTO refund;
    private Integer review;

    public void setDTOByEntity(OrderItem orderItem){
        id = orderItem.getId();
        count = orderItem.getCount();
        totalPrice = orderItem.getTotalPrice();
        orderId = orderItem.getOrder().getId();
        orderDate = orderItem.getOrder().getOrderDate();
        productId = orderItem.getProduct().getId();
        review = orderItem.getReview();
        product = new ProductDTO();
        product.setDTOByEntity(orderItem.getProduct());
        Refund refundInstance = orderItem.getOrder().getRefund();
        if(refund != null){
            refund = new RefundDTO();
            refund.setDTOByEntity(refundInstance);
        }
    }
}
