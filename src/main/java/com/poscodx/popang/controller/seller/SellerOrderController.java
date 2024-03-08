package com.poscodx.popang.controller.seller;


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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/seller/order/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class SellerOrderController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final OrderService orderService;
    private final int PAGE_SIZE = 8;

    // (판매자) 환불 조회
    @GetMapping("refunds")
    public String moveToRefundsItemsPage(Authentication auth, Model model, @RequestParam(defaultValue = "0") int pageNumber){
        UserDTO login = (UserDTO) auth.getPrincipal();
        String loginId = login.getId();

        if (pageNumber <= 0) pageNumber = 1;
        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        Page<OrderItemDTO> refundItemsPage = orderService.findRefundItemsBySeller(loginId, page);
        int totalPage = refundItemsPage.getTotalPages();
        long totalElement = refundItemsPage.getTotalElements();
        List<OrderItemDTO> refundItemList = refundItemsPage.getContent();

        int startPage = (((pageNumber - 1) / PAGE_SIZE) * 10) + 1;
        int endPage = Math.min(startPage + PAGE_SIZE - 1, totalPage);

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalElement", totalElement);
        model.addAttribute("refundItemList", refundItemList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if (totalPage > 0 && pageNumber > totalPage)
            return "redirect:/seller/order/refunds?pageNumber=" + totalPage;
        return "order/seller/refund_items";
    }


    // (판매자) 주문 상품 조회
    @GetMapping("items")
    public String moveToOrderItemsPage(Authentication auth, Model model, @RequestParam(defaultValue = "0") int pageNumber){
        UserDTO login = (UserDTO) auth.getPrincipal();
        String loginId = login.getId();

        if (pageNumber <= 0) pageNumber = 1;
        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        Page<OrderItemDTO> orderItemsPage = orderService.findOrderItemsBySeller(loginId, page);
        int totalPage = orderItemsPage.getTotalPages();
        long totalElement = orderItemsPage.getTotalElements();
        List<OrderItemDTO> orderItemList = orderItemsPage.getContent();

        int startPage = (((pageNumber - 1) / PAGE_SIZE) * 10) + 1;
        int endPage = Math.min(startPage + PAGE_SIZE - 1, totalPage);

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalElement", totalElement);
        model.addAttribute("orderItemList", orderItemList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if (totalPage > 0 && pageNumber > totalPage)
            return "redirect:/seller/order/order_items?pageNumber=" + totalPage;
        return "order/seller/order_items";
    }

    // 배송 완료하기
    @PostMapping("delivery/finish")
    @ResponseBody
    public RestResponseDTO finishDelivery(Long deliveryId){
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("배송 성공!");
        orderService.changeDeliveryStatus(deliveryId, 3L);
        return res;
    }

    // 배송하기
    @PostMapping("delivery/start")
    @ResponseBody
    public RestResponseDTO deliver(Long deliveryId){
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("배송 성공!");
        orderService.changeDeliveryStatus(deliveryId, 2L);
        return res;
    }

    // 배송 페이지 확인
    @GetMapping("{orderId}/delivery")
    public String moveToDeliveryPage(Authentication auth, @PathVariable Long orderId, Model model){
        UserDTO login = (UserDTO) auth.getPrincipal();
        AuthDTO authDTO = new AuthDTO();
        authDTO.setByUserDTO(login);
        OrderDTO order = orderService.findOrderById(orderId);

        model.addAttribute("order", order);
        model.addAttribute("auth", authDTO);


        return "order/delivery/delivery";
    }

    // 주문 페이지로 이동
    @GetMapping("orders")
    public String moveToOrdersPage(Authentication auth, Model model, @RequestParam(defaultValue = "0") int pageNumber){
        UserDTO login = (UserDTO) auth.getPrincipal();
        String loginId = login.getId();

        if (pageNumber <= 0) pageNumber = 1;
        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        Page<OrderDTO> orderPage = orderService.findOrdersBySeller(loginId, page);
        int totalPage = orderPage.getTotalPages();
        long totalElement = orderPage.getTotalElements();
        List<OrderDTO> orderList = orderPage.getContent();

        int startPage = (((pageNumber - 1) / PAGE_SIZE) * 10) + 1;
        int endPage = Math.min(startPage + PAGE_SIZE - 1, totalPage);

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalElement", totalElement);
        model.addAttribute("orderList", orderList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if (totalPage > 0 && pageNumber > totalPage)
            return "redirect:/seller/order/orders?pageNumber=" + totalPage;
        if(login.getRole() == 2)
            return "order/seller/orders";
        else
            return "order/admin/orders";
    }
}
