package com.poscodx.popang.repository;

import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.Orders;
import com.poscodx.popang.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @EntityGraph(attributePaths = {"orderItemList"})
    Page<Orders> findAllByUserOrderByOrderDateDesc(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"orderItemList"})
    Page<Orders> findAllByDeliveryUserOrderByOrderDateDesc(User user, Pageable pageable);
}
