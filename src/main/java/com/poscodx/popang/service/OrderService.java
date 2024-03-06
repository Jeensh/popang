package com.poscodx.popang.service;

import com.poscodx.popang.domain.*;
import com.poscodx.popang.domain.dto.*;
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
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AddressRepository addressRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public void addCartItem(CartItemDTO cartItemDTO, String userId){
        // 카트 불러오기 없으면 하나 생성
        User user = userRepository.findUserById(userId);
        Cart cart = cartRepository.findByUser(user);
        if(cart == null){
            Cart newCart = new Cart();
            cart = cartRepository.save(newCart);
        }

        // 카트에 아이템 담기
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setCount(cartItemDTO.getCount());
        Product product = productRepository.findProductById(cartItemDTO.getProductId());
        cartItem.setProduct(product);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void rapidOrder(OrderItemDTO orderItemDTO, String userId, AddressDTO addressDTO){
        // 상품 수량 감소
        User user = userRepository.findUserById(userId);
        Product product = productRepository.findProductById(orderItemDTO.getProductId());
        product.setStock(product.getStock() - orderItemDTO.getCount());
        productRepository.save(product);

        // 주소 만들기
        Address address = new Address();
        address.setMainAddress(addressDTO.getMainAddress());
        address.setDetailAddress(addressDTO.getDetailAddress());
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        // 배송 만들기
        Delivery delivery = new Delivery();
        User seller = userRepository.findUserById(product.getSeller().getId());
        delivery.setAddress(savedAddress);
        delivery.setUser(seller);
        delivery.setStatus(1L);
        Delivery savedDelivery = deliveryRepository.save(delivery);

        // 주문 만들기
        Orders order = new Orders();
        LocalDateTime nowDateTime = LocalDateTime.now();
        Timestamp nowTimeStamp = Timestamp.valueOf(nowDateTime);
        Long orderPrice = orderItemDTO.getTotalPrice();
        OrderItem orderItem = addOrderItem(orderItemDTO);

        order.setOrderDate(nowTimeStamp);
        order.setOrderPrice(orderPrice);
        order.setDiscountAmount(0L);
        order.setStatus(2L);
        order.setUser(user);
        order.setDelivery(savedDelivery);
        Orders savedOrder = orderRepository.save(order);
        orderItem.setOrder(savedOrder);
        orderItemRepository.save(orderItem);
    }



    // orderItem 만들기
    @Transactional
    public OrderItem addOrderItem(OrderItemDTO orderItemDTO){
        OrderItem orderItem = new OrderItem();
        Product product = productRepository.findProductById(orderItemDTO.getProductId());
        orderItem.setProduct(product);
        orderItem.setCount(orderItemDTO.getCount());
        Long totalPrice = product.getPrice() * orderItem.getCount();
        orderItem.setTotalPrice(totalPrice);

        return orderItemRepository.save(orderItem);
    }

    // orderItem 조회
    public OrderItemDTO findOrderItemById(Long id){
        OrderItem orderItem = orderItemRepository.findById(id).get();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setDTOByEntity(orderItem);
        return orderItemDTO;
    }
}
