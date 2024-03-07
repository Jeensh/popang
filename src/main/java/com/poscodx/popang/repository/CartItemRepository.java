package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Cart;
import com.poscodx.popang.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCart(Cart cart);
}
