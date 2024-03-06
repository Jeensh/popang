package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Timestamp orderDate;
    private Long orderPrice;
    private Long discountAmount;
    private Long status;
    private String userId;
    private Long deliveryId;
    private Long couponId;
    private List<OrderItem> orderItemList = new ArrayList<>();

    public void setDTOByEntity(Orders order){
        id = order.getId();
        orderDate = order.getOrderDate();
        orderPrice = order.getOrderPrice();
        discountAmount = order.getDiscountAmount();
        status = order.getStatus();
        userId = order.getUser().getId();
    }

}
