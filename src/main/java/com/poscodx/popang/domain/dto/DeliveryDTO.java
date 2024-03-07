package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
public class DeliveryDTO {
    private Long id;
    private Long status;
    private AddressDTO address;
    private Timestamp departureDate;
    private Timestamp arrivalDate;
    private AuthDTO sender;

    public void setDTOByEntity(Delivery delivery){
        id = delivery.getId();
        status = delivery.getStatus();
        departureDate = delivery.getDepartureDate();
        arrivalDate = delivery.getArrivalDate();
        Address ad = delivery.getAddress();
        address = new AddressDTO();
        address.setDTOByEntity(ad);
        sender = new AuthDTO();
        User user = delivery.getUser();
        sender.setId(user.getId());
        sender.setRole(user.getRole());
        sender.setName(user.getName());
    }
}
