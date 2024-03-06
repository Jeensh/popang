package com.poscodx.popang.repository;

import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.ProductCategory;
import com.poscodx.popang.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
