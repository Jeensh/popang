package com.poscodx.popang.controller;


import com.poscodx.popang.domain.Cart;
import com.poscodx.popang.domain.CartItem;
import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.User;
import com.poscodx.popang.domain.dto.*;
import com.poscodx.popang.service.*;
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
    private final ReplyService replyService;
    private final int PAGE_SIZE = 8;

    // 초기 리뷰 작성
    @PostMapping("review/write")
    @ResponseBody
    public RestResponseDTO writeReview(Authentication auth, ReplyDTO replyDTO, Long orderItemId){
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("리뷰가 등록되었습니다!");
        UserDTO login = (UserDTO) auth.getPrincipal();
        replyService.writeReview(replyDTO, login.getId(), replyDTO.getProductId(), orderItemId);
        return res;
    }

    // 리뷰 가능한 상품 페이지 이동
    @GetMapping("reviews")
    public String moveToReviewItemsPage(Authentication auth, Model model, @RequestParam(defaultValue = "0") int pageNumber){
        UserDTO login = (UserDTO) auth.getPrincipal();
        String loginId = login.getId();

        if (pageNumber <= 0) pageNumber = 1;
        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        Page<OrderItemDTO> reviewItemsPage = orderService.findReviewItemsByUser(loginId, page);
        int totalPage = reviewItemsPage.getTotalPages();
        long totalElement = reviewItemsPage.getTotalElements();
        List<OrderItemDTO> reviewItemList = reviewItemsPage.getContent();

        int startPage = (((pageNumber - 1) / PAGE_SIZE) * 10) + 1;
        int endPage = Math.min(startPage + PAGE_SIZE - 1, totalPage);

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalElement", totalElement);
        model.addAttribute("reviewItemList", reviewItemList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if (totalPage > 0 && pageNumber > totalPage)
            return "redirect:/order/reviews?pageNumber=" + totalPage;
        return "order/review_items";
    }

    // 환불 완료하기
    @PostMapping("refund/delivery/finish")
    @ResponseBody
    public RestResponseDTO finishDelivery(Long deliveryId){
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("배송 성공!");
        orderService.finishRefund(deliveryId);
        return res;
    }

    // 환불 배송하기
    @PostMapping("refund/delivery/start")
    @ResponseBody
    public RestResponseDTO deliver(Long deliveryId){
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("배송 성공!");
        orderService.changeDeliveryStatus(deliveryId, 2L);
        return res;
    }

    // 환불 배송 페이지 확인
    @GetMapping("refund/{refundId}/delivery")
    public String moveToRefundDeliveryPage(Authentication auth, @PathVariable Long refundId, Model model){
        UserDTO login = (UserDTO) auth.getPrincipal();
        AuthDTO authDTO = new AuthDTO();
        authDTO.setByUserDTO(login);
        RefundDTO refund = orderService.findRefundById(refundId);

        model.addAttribute("refund", refund);
        model.addAttribute("auth", authDTO);

        return "order/delivery/refund_delivery";
    }

    // 환불 페이지 확인
    @GetMapping("refunds")
    public String moveToRefundsPage(Authentication auth, Model model, @RequestParam(defaultValue = "0") int pageNumber){
        UserDTO login = (UserDTO) auth.getPrincipal();
        String loginId = login.getId();

        if (pageNumber <= 0) pageNumber = 1;
        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        Page<RefundDTO> refundPage = orderService.findAllRefundByUser(loginId, page);
        int totalPage = refundPage.getTotalPages();
        long totalElement = refundPage.getTotalElements();
        List<RefundDTO> refundList = refundPage.getContent();

        int startPage = (((pageNumber - 1) / PAGE_SIZE) * 10) + 1;
        int endPage = Math.min(startPage + PAGE_SIZE - 1, totalPage);

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalElement", totalElement);
        model.addAttribute("refundList", refundList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        if (totalPage > 0 && pageNumber > totalPage)
            return "redirect:/order/refunds?pageNumber=" + totalPage;
        return "order/refund/refunds";
    }

    // 환불 신청
    @PostMapping("refund")
    @ResponseBody
    public RestResponseDTO enrollRefund(Authentication auth, Long orderId, String reason, AddressDTO addressDTO){
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("환불 성공!");

        // 주문 상태 검증
        OrderDTO orderDTO = orderService.findOrderById(orderId);
        if(orderDTO.getStatus() != 2L){
            res.setSuccess(false);
            res.setMessage("주문이 결제완료 상태가 아닙니다!");
        }

        // 사용자 검증
        if(!login.getId().equals(orderDTO.getUserId())){
            res.setSuccess(false);
            res.setMessage("본인의 주문만 환불 할 수 있습니다!");
        }

        if(res.isSuccess()){
            orderService.enrollRefund(orderId, reason, addressDTO);
        }
        return res;
    }

    // 주문 배송 페이지 확인
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

    // (구매자) 주문 상태변경 (결제전 -> 결제후, 결제후 -> 구매확정 or 환불)
    @PostMapping("confirm")
    @ResponseBody
    public RestResponseDTO confirmOrder(Authentication auth, Long orderId){
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("이제 환불하실 수 없습니다.");
        OrderDTO orderDTO = orderService.findOrderById(orderId);
        if(orderDTO.getId() == null){
            res.setSuccess(false);
            res.setMessage("존재하지 않는 주문입니다.");
        }
        if(!orderDTO.getUserId().equals(login.getId())){
            res.setSuccess(false);
            res.setMessage("주문과 주문자가 일치하지 않습니다");
        }
        if(res.isSuccess())
            orderService.changeOrderStatus(orderId, 3L);
        return res;
    }

    // 주문 페이지로 이동
    @GetMapping("orders")
    public String moveToOrdersPage(Authentication auth, Model model, @RequestParam(defaultValue = "0") int pageNumber){
        UserDTO login = (UserDTO) auth.getPrincipal();
        String loginId = login.getId();

        if (pageNumber <= 0) pageNumber = 1;
        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        Page<OrderDTO> orderPage = orderService.findOrdersByUser(loginId, page);
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
            return "redirect:/order/orders?pageNumber=" + totalPage;
        return "order/orders";
    }

    // 장바구니 선택 구매(주문 생성) - 상품 수량 줄어듬
    @PostMapping("cart/purchase")
    @ResponseBody
    public RestResponseDTO orderByCart(Authentication auth,
                                       AddressDTO addressDTO,
                                       @RequestParam(value = "idList[]") List<Long> cartItemIdList){
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage("구매 완료!");

        // 사용자 검증
        if(login.getRole() != 1L){
            res.setMessage("상품 구매는 사용자만 가능합니다.");
            res.setSuccess(false);
        }

        // 상품 유무 검증
        List<CartItemDTO> cartItemDTOList = orderService.findSelectedCartItem(cartItemIdList);
        for(CartItemDTO item : cartItemDTOList){
            ProductDTO product = productService.findById(item.getProductId());

            // 수량 확인
            if(item.getCount() == null || item.getCount() < 1){
                res.setMessage("정확한 수량을 입력해주세요.");
                res.setSuccess(false);
                break;
            }
            else if(product.getStock() < item.getCount()){
                res.setMessage(product.getName() + "의 수량이 부족합니다.");
                res.setSuccess(false);
                break;
            }
        }

        // 주소 확인
        String mainAddress = addressDTO.getMainAddress();
        String detailAddress = addressDTO.getDetailAddress();
        if(mainAddress == null || detailAddress == null || mainAddress.isEmpty() || detailAddress.isEmpty()){
            res.setMessage("주소를 모두 입력해주세요.");
            res.setSuccess(false);
        }

        // 주문 생성하고 구매한 cart-item 비우기
        if(res.isSuccess())
            orderService.selectOrder(login.getId(), cartItemDTOList, addressDTO);
        return res;
    }

    // 장바구니 비우기
    @PostMapping("cart/clear")
    @ResponseBody
    public RestResponseDTO clearCart(Long cartId) {
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        orderService.clearCart(cartId);
        return res;
    }

    // 장바구니 아이템 삭제
    @PostMapping("cart/delete")
    @ResponseBody
    public RestResponseDTO deleteCartItem(Long carItemId){
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        orderService.deleteCartItem(carItemId);
        return res;
    }

    // 장바구니 페이지 열기
    @GetMapping("cart")
    public String moveCartPage(Authentication auth, Model model){
        // 사용자 검증
        UserDTO login = (UserDTO) auth.getPrincipal();
        if(login.getRole() != 1L) return "redirect:/";

        // 장바구니 아이템 가져오기
        CartDTO cart = orderService.findCartByUserId(login.getId());

        model.addAttribute("cart", cart);
        return "order/cart";
    }

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

        // 장바구니에 추가하기
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
