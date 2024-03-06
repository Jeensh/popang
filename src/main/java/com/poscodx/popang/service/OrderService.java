package com.poscodx.popang.service;

import com.poscodx.popang.domain.*;
import com.poscodx.popang.domain.dto.OrderDTO;
import com.poscodx.popang.domain.dto.OrderItemDTO;
import com.poscodx.popang.domain.dto.ProductDTO;
import com.poscodx.popang.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // order 조회

    // orderItem List받아서 Order 생성하기 => orderItem은 이미 저장되어 있다고 가정
//    orderDate 넣어야됨
//    orderPrice 넣어야됨
//    discountAmount 넣어야됨
//    status 넣어야됨 1로
//    user 넣어야됨
//    orderItem List 넣어줘야됨
    @Transactional
    public void addOrder(List<OrderItemDTO> orderItemDTOList, String userId){
        Orders order = new Orders();
        User user = userRepository.findUserById(userId);
        LocalDateTime nowDateTime = LocalDateTime.now();
        Timestamp nowTimeStamp = Timestamp.valueOf(nowDateTime);
        Long orderPrice = 0L;
        for(OrderItemDTO o : orderItemDTOList) orderPrice += o.getTotalPrice();

        order.setOrderDate(nowTimeStamp);
        order.setOrderPrice(orderPrice);
        order.setDiscountAmount(0L);
        order.setStatus(1L);
        order.setUser(user);
        order.setOrderItemList(
                orderItemDTOList.stream().map(dto -> {
                    OrderItem orderItem = orderItemRepository.findById(dto.getId()).get();
                    return orderItem;
                }).toList());
        orderRepository.save(order);
    }



    // orderItem 만들기
    @Transactional
    public OrderItemDTO addOrderItem(OrderItemDTO orderItemDTO){
        OrderItem orderItem = new OrderItem();
        Product product = productRepository.findProductById(orderItemDTO.getProductId());
        orderItem.setProduct(product);
        orderItem.setCount(orderItemDTO.getCount());
        Long totalPrice = product.getPrice() * orderItem.getCount();
        orderItem.setTotalPrice(totalPrice);

        OrderItem newOrderItem = orderItemRepository.save(orderItem);
        OrderItemDTO newOrderItemDTO = new OrderItemDTO();
        newOrderItemDTO.setDTOByEntity(newOrderItem);
        return newOrderItemDTO;
    }

    // orderItem 조회
    public OrderItemDTO findOrderItemById(Long id){
        OrderItem orderItem = orderItemRepository.findById(id).get();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setDTOByEntity(orderItem);
        return orderItemDTO;
    }
}
