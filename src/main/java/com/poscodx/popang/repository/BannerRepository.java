package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Banner;
import com.poscodx.popang.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAll();
}
