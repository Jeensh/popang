package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.Address;
import com.poscodx.popang.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddressDTO {
    private Long id;
    private String mainAddress;
    private String detailAddress;
    private String userId;

    public void setDTOByEntity(Address address){
        id = address.getId();
        mainAddress = address.getMainAddress();
        detailAddress = address.getDetailAddress();
        userId = address.getUser().getId();
    }
}
