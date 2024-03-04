package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);
    Page<Product> findAllProductByOrderByUploadDateDesc(Pageable pageable);

    void deleteById(Long id);

}
