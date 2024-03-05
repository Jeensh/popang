package com.poscodx.popang.service;

import com.poscodx.popang.domain.ProductCategory;
import com.poscodx.popang.domain.dto.CategorySetDTO;
import com.poscodx.popang.domain.dto.ProductCategoryDTO;
import com.poscodx.popang.repository.CategoryRepository;
import com.poscodx.popang.repository.ProductRepository;
import com.poscodx.popang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<ProductCategoryDTO> findAllByDepth(Long depth){
        return categoryRepository.findAllByDepthOrderByName(depth)
                .stream().map(c -> {
                    ProductCategoryDTO p = new ProductCategoryDTO();
                    p.setDTOByEntity(c);
                    return p;
                }).toList();
    }

    public String getCategoryPath(Long code){

        return "";
    }

    public ProductCategoryDTO findByCode(Long code){
        ProductCategoryDTO pcDTO = new ProductCategoryDTO();
        ProductCategory pc = categoryRepository.findByCode(code);
        if(pc != null)
            pcDTO.setDTOByEntity(pc);
        return pcDTO;
    }

    public List<ProductCategoryDTO> getChildCategories(Long largeCode){
        ProductCategory pc = categoryRepository.findByCode(largeCode);
        List<ProductCategoryDTO> childList = pc.getCategoryList()
                .stream().map(c -> {
                    ProductCategoryDTO dto = new ProductCategoryDTO();
                    dto.setDTOByEntity(c);
                    return dto;
                }).toList();
        return childList;
    }

    @Transactional
    // flag = 1(대중소), 2(대중), 3(대)
    public void addSet(CategorySetDTO set, int flag){
        ProductCategory cl = null;
        ProductCategory cm = null;
        ProductCategory cs = null;
        ProductCategory root = categoryRepository.findByCode(0L);

        if(flag <= 3){
            cl = categoryRepository.findByCode(set.getLargeCode());
            if(cl == null){
                cl = new ProductCategory();
                cl.setParentCategory(root);
                cl.setCode(set.getLargeCode());
                cl.setDepth(1L);
            }
            cl.setName(set.getLargeName());
        }
        if(flag <= 2){
            cm = categoryRepository.findByCode(set.getMediumCode());
            if(cm == null){
                cm = new ProductCategory();
                cm.setParentCategory(cl);
                cm.setCode(set.getMediumCode());
                cm.setDepth(2L);
            }
            cm.setName(set.getMediumName());
        }
        if(flag <= 1){
            cs = categoryRepository.findByCode(set.getSmallCode());
            if(cs == null){
                cs = new ProductCategory();
                cs.setParentCategory(cm);
                cs.setCode(set.getSmallCode());
                cs.setDepth(3L);
            }
            cs.setName(set.getSmallName());
        }
        if(cl != null)
            categoryRepository.save(cl);
        if(cm != null)
            categoryRepository.save(cm);
        if(cs != null)
            categoryRepository.save(cs);
    }
}
