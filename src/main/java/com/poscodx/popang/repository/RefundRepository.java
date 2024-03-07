package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Orders;
import com.poscodx.popang.domain.Refund;
import com.poscodx.popang.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    Page<Refund> findAllByOrders_UserOrderByEnrollDateDesc(User user, Pageable pageable);
}
