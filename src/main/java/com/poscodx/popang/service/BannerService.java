package com.poscodx.popang.service;

import com.poscodx.popang.domain.Banner;
import com.poscodx.popang.domain.ProductCategory;
import com.poscodx.popang.domain.dto.CategorySetDTO;
import com.poscodx.popang.domain.dto.ProductCategoryDTO;
import com.poscodx.popang.repository.BannerRepository;
import com.poscodx.popang.repository.CategoryRepository;
import com.poscodx.popang.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BannerService {
    private final BannerRepository bannerRepository;

    public List<String> findAllByDepth(Long depth) {
        return bannerRepository.findAll()
                .stream().map(Banner::getAddress).toList();
    }

    @Transactional
    public void addBanner(String address) {
        Banner banner = new Banner();
        banner.setAddress(address);
        bannerRepository.save(banner);
    }
}
