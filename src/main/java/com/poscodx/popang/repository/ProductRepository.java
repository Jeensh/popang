package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = {"imageList"})
    Product findProductById(Long id);

    @EntityGraph(attributePaths = {"imageList"})
    Page<Product> findAllProductBySellerOrderByUploadDateDesc(User user, Pageable pageable);
    void deleteById(Long id);

}
