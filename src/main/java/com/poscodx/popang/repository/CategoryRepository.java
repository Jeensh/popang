package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findAllByDepthOrderByName(Long depth);
    ProductCategory findByCode(Long code);
}
