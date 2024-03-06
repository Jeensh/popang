package com.poscodx.popang.controller;


import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.dto.*;
import com.poscodx.popang.service.CategoryService;
import com.poscodx.popang.service.OrderService;
import com.poscodx.popang.service.ProductService;
import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/order/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final OrderService orderService;

    // 장바구니 수정
    // 장바구니 선택 구매(주문 생성) - 상품 수량 줄어들지 않음
    // (구매자) 주문 상태변경 (결제전 -> 결제후, 결제후 -> 구매확정 or 환불)

    // 장바구니 추가 - 상품 수량 줄어들지 않음
    @PostMapping("cart/add")
    @ResponseBody
    public RestResponseDTO addCartItem(Authentication auth, CartItemDTO cartItemDTO){
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("장바구니에 추가 완료!");

        // 사용자 검증
        if(login.getRole() != 1L){
            res.setMessage("상품 구매는 사용자만 가능합니다.");
            res.setSuccess(false);
        }

        // 상품 확인
        ProductDTO product = productService.findById(cartItemDTO.getProductId());
        if(product == null){
            res.setMessage("해당 상품이 존재하지 않습니다.");
            res.setSuccess(false);
        }

        // 수량 확인
        if(cartItemDTO.getCount() == null || cartItemDTO.getCount() < 1){
            res.setMessage("정확한 수량을 입력해주세요.");
            res.setSuccess(false);
        }

        // 주문 생성하기
        if(res.isSuccess())
            orderService.addCartItem(cartItemDTO, login.getId());
        return res;
    }

    // 즉시 구매(주문 생성) - 상품 수량 줄어들어야 함
    @PostMapping("purchase")
    @ResponseBody
    public RestResponseDTO purchase(Authentication auth, OrderItemDTO orderItem, AddressDTO address){
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("구매 완료!");

        // 사용자 검증
        if(login.getRole() != 1L){
            res.setMessage("상품 구매는 사용자만 가능합니다.");
            res.setSuccess(false);
        }

        ProductDTO product = productService.findById(orderItem.getProductId());
        if(product == null){
            res.setMessage("해당 상품이 존재하지 않습니다.");
            res.setSuccess(false);
        }

        // 수량 확인
        if(orderItem.getCount() == null || orderItem.getCount() < 1){
            res.setMessage("정확한 수량을 입력해주세요.");
            res.setSuccess(false);
        }
        else if(product.getStock() < orderItem.getCount()){
            res.setMessage("재고보다 많은 수량을 구매할 수 없습니다.");
            res.setSuccess(false);
        }

        // 주소 확인
        String mainAddress = address.getMainAddress();
        String detailAddress = address.getDetailAddress();
        if(mainAddress == null || detailAddress == null || mainAddress.isEmpty() || detailAddress.isEmpty()){
            res.setMessage("주소를 모두 입력해주세요.");
            res.setSuccess(false);
        }

        // 주문 생성하기
        if(res.isSuccess())
            orderService.rapidOrder(orderItem, login.getId(), address);
        return res;
    }
}
