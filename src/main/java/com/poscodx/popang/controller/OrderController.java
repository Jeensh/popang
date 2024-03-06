package com.poscodx.popang.controller;


import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.dto.*;
import com.poscodx.popang.service.CategoryService;
import com.poscodx.popang.service.ProductService;
import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RequestMapping("/order/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    // 장바구니 추가 - 상품 수량 줄어들지 않음
    // 장바구니 수정
    // 장바구니 선택 구매(주문 생성) - 상품 수량 줄어들지 않음
    // (구매자) 주문 상태변경 (결제전 -> 결제후, 결제후 -> 구매확정 or 환불)

    // 즉시 구매(주문 생성) - 상품 수량 줄어들어야 함
    @PostMapping("purchase")
    public RestResponseDTO purchase(OrderItemDTO orderItem){
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(false);

        ProductDTO product = productService.findById(orderItem.getProductId());
        if(product == null){
            res.setMessage("해당 상품이 존재하지 않습니다.");
        }

        return res;
    }
}
