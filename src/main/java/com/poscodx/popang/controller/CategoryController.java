package com.poscodx.popang.controller;


import com.poscodx.popang.domain.ProductCategory;
import com.poscodx.popang.domain.dto.ProductCategoryDTO;
import com.poscodx.popang.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/category/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    private static final int PAGE_SIZE = 10;

    @PostMapping("{parentCode}")
    public String getChildCategories(@PathVariable Long parentCode, Model model){
        List<ProductCategoryDTO> list =  categoryService.getChildCategories(parentCode);
        model.addAttribute("Categories", list);
        model.addAttribute("depth", categoryService.findByCode(parentCode).getDepth() + 1);
        return "product/category_items";
    }

    @PostMapping("menu")
    public String getCategoryMenu(Model model){
        List<ProductCategory> largeCategories = categoryService.findLargeForMenu();
        model.addAttribute("largeCategories", largeCategories);
        return "component/category_menu";
    }
}
