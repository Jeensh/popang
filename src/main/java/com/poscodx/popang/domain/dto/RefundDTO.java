package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.Delivery;
import com.poscodx.popang.domain.Orders;
import com.poscodx.popang.domain.Refund;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
public class RefundDTO {
    private Long id;
    private String reason;
    private Long status;
    private OrderDTO order;
    private DeliveryDTO delivery;
    private Timestamp enrollDate;
    private Timestamp refundDate;

    public void setDTOByEntity(Refund refund){
        id = refund.getId();
        reason = refund.getReason();
        status = refund.getStatus();
        enrollDate = refund.getEnrollDate();
        refundDate = refund.getRefundDate();
        order = new OrderDTO();
        delivery = new DeliveryDTO();
        order.setDTOByEntity(refund.getOrders());
        delivery.setDTOByEntity(refund.getDelivery());
    }
}
