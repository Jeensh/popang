package com.poscodx.popang.service;

import com.poscodx.popang.domain.*;
import com.poscodx.popang.domain.dto.*;
import com.poscodx.popang.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // 주문 상태 바꾸기
    @Transactional
    public void changeOrderStatus(Long orderId, Long status){
        Orders order = orderRepository.findById(orderId).get();
        order.setStatus(status);
        orderRepository.save(order);
    }

    // 주문 찾기
    public OrderDTO findOrderById(Long id){
        OrderDTO dto = new OrderDTO();
        Orders order = orderRepository.findById(id).get();
        dto.setDTOByEntity(order);
        return dto;
    }

    // 유저아이디로 주문 목록 페이지 받아오기
    public Page<OrderDTO> findOrdersByUser(String userId, Pageable pageable){
        User user = userRepository.findUserById(userId);
        return orderRepository.findAllByUserOrderByOrderDateDesc(user, pageable)
                .map(o -> {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setDTOByEntity(o);
                    List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
                    for(OrderItem oi : o.getOrderItemList()){
                        OrderItemDTO orderItemDTO = new OrderItemDTO();
                        orderItemDTO.setDTOByEntity(oi);
                        ProductDTO productDTO = new ProductDTO();
                        productDTO.setDTOByEntity(oi.getProduct());
                        orderItemDTO.setProduct(productDTO);
                        orderItemDTOList.add(orderItemDTO);
                    }
                    orderDTO.setOrderItemList(orderItemDTOList);
                    return orderDTO;
                });
    }

    // cartItemDTO 받아오기
    public List<CartItemDTO> findSelectedCartItem(List<Long> cartItemIdList){
        List<CartItemDTO> list = new ArrayList<>();
        for(Long id : cartItemIdList){
            CartItem cartItem = cartItemRepository.findById(id).get();
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setDTOByEntity(cartItem);
            list.add(cartItemDTO);
        }
        return list;
    }

    // 선택항목 구매하기(이 경우 seller는 관리자)
    @Transactional
    public void selectOrder(String userId, List<CartItemDTO> cartItemDTOList, AddressDTO addressDTO){
        // 주문 생성
        Orders order = new Orders();

        // 상품 수량 감소 및 cart-itme -> order-item
        User user = userRepository.findUserById(userId);
        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderPrice = 0L;
        for(CartItemDTO item : cartItemDTOList){
            OrderItem orderItem = new OrderItem();
            Product product = productRepository.findProductById(item.getProductId());
            product.setStock(product.getStock() - item.getCount());
            productRepository.save(product);

            orderItem.setProduct(product);
            orderItem.setCount(item.getCount());
            orderItem.setTotalPrice(product.getPrice() * orderItem.getCount());
            orderPrice += orderItem.getTotalPrice();
            orderItemList.add(orderItem);

            // 주문한 cart-item 삭제
            cartItemRepository.deleteById(item.getId());
        }

        // 주소 만들기
        Address address = new Address();
        address.setMainAddress(addressDTO.getMainAddress());
        address.setDetailAddress(addressDTO.getDetailAddress());
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);

        // 배송 만들기
        Delivery delivery = new Delivery();
        User seller = userRepository.findUserById("root");
        delivery.setAddress(savedAddress);
        delivery.setUser(seller);
        delivery.setStatus(1L);
        Delivery savedDelivery = deliveryRepository.save(delivery);

        // 주문값 세팅 후 저장
        LocalDateTime nowDateTime = LocalDateTime.now();
        Timestamp nowTimeStamp = Timestamp.valueOf(nowDateTime);
        order.setOrderDate(nowTimeStamp);
        order.setOrderPrice(orderPrice);
        order.setDiscountAmount(0L);
        order.setStatus(2L);
        order.setUser(user);
        order.setDelivery(savedDelivery);
        Orders savedOrder = orderRepository.save(order);

        // orderItem들 주문하고 연결
        for(OrderItem item : orderItemList){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
    }

    // 장바구니 비우기
    @Transactional
    public void clearCart(Long cartId){
        Cart cart = cartRepository.findById(cartId).get();
        cartItemRepository.deleteAllByCart(cart);
    }

    // 장바구니 아이템 삭제
    @Transactional
    public void deleteCartItem(Long id){
        cartItemRepository.deleteById(id);
    }

    @Transactional
    // 장바구니 가져오기
    public CartDTO findCartByUserId(String userId){
        // 카트 불러오기 없으면 하나 생성
        User user = userRepository.findUserById(userId);
        CartDTO cartDTO = new CartDTO();
        Cart cart = cartRepository.findByUser(user);
        if(cart == null){
            Cart newCart = new Cart();
            newCart.setUser(user);
            cart = cartRepository.save(newCart);
        }
        cartDTO.setId(cart.getId());

        // 카트에 아이템 담기
        List<CartItem> cartItemList = cart.getCartItemList();
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for(CartItem ci : cartItemList){
            // 카트 아이템 정보 세팅
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setDTOByEntity(ci);

            // 상품 정보 세팅
            ProductDTO productDTO = new ProductDTO();
            Product product = ci.getProduct();
            productDTO.setDTOByEntity(product);
            List<ProductImage> imageList = product.getImageList();
            for(ProductImage image : imageList){
                productDTO.getImageList().add(image);
            }
            productDTO.setDescriptionDetail(null);

            // 카트 아이템에 상품 정보 담기
            cartItemDTO.setProduct(productDTO);
            cartItemDTOList.add(cartItemDTO);
        }
        cartDTO.setCartItemList(cartItemDTOList);

        return cartDTO;
    }

    @Transactional
    public void addCartItem(CartItemDTO cartItemDTO, String userId){
        // 카트 불러오기 없으면 하나 생성
        User user = userRepository.findUserById(userId);
        Cart cart = cartRepository.findByUser(user);
        if(cart == null){
            Cart newCart = new Cart();
            newCart.setUser(user);
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
