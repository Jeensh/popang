package com.poscodx.popang.controller.rest;


import com.poscodx.popang.domain.dto.CategorySetDTO;
import com.poscodx.popang.domain.dto.ProductCategoryDTO;
import com.poscodx.popang.domain.dto.RestResponseDTO;
import com.poscodx.popang.service.CategoryService;
import com.poscodx.popang.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/rest/categories/")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RestCategoryController {
    private final CategoryService categoryService;

    @RequestMapping()
    public List<ProductCategoryDTO> getLargeCategories(){
        return categoryService.findAllByDepth(1L);
    }

    @RequestMapping("{largeCode}")
    public List<ProductCategoryDTO> getMediumCategories(@PathVariable Long largeCode){
        return categoryService.getChildCategories(largeCode);
    }

    @RequestMapping("{largeCode}/{mediumCode}")
    public List<ProductCategoryDTO> getSmallCategories(@PathVariable Long largeCode, @PathVariable Long mediumCode){
        if(categoryService.findByCode(mediumCode).getCode() != largeCode) return null;
        return categoryService.getChildCategories(mediumCode);
    }

    @RequestMapping("add")
    public RestResponseDTO add(CategorySetDTO set){
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);

        // 대중소 전부 입력하는 경우
        if(set.getLargeCode() != null && set.getMediumCode() != null && set.getSmallCode() != null){
            categoryService.addSet(set, 1);
        }
        // 대중까지만 입력하는 경우
        else if(set.getLargeCode() != null && set.getMediumCode() != null){
            categoryService.addSet(set, 2);
        }
        // 대까지만 입력하는 경우
        else if(set.getSmallCode() != null){
            categoryService.addSet(set, 3);
        }
        else{
            res.setSuccess(false);
            res.setMessage("잘못된 입력입니다.");
        }
        return res;
    }

    private boolean checkCode(ProductCategoryDTO pc){
        return categoryService.findByCode(pc.getCode()).getCode() == null;
    }
}
