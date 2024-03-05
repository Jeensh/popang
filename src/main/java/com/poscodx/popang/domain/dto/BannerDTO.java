package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.Banner;
import lombok.Data;

@Data
public class BannerDTO {
    private Long id;
    private String address;

    public void setDTOByEntity(Banner banner){
        id = banner.getId();
        address = banner.getAddress();
    }
}
