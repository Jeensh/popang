package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Banner;
import com.poscodx.popang.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
