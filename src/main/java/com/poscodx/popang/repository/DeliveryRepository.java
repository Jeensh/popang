package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Delivery;
import com.poscodx.popang.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
