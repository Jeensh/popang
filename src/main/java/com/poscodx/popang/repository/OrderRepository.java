package com.poscodx.popang.repository;

import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
