package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.CartItem;
import com.poscodx.popang.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {
    private Long id;
    List<CartItemDTO> cartItemList = new ArrayList<>();
}
