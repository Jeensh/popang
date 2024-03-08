package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Banner;
import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findAllByProduct(Product product, Pageable pageable);
}
