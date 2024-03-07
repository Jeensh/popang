package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Cart;
import com.poscodx.popang.domain.Orders;
import com.poscodx.popang.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = {"cartItemList"})
    Cart findByUser(User user);
}
