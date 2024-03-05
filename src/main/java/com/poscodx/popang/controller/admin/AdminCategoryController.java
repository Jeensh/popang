package com.poscodx.popang.controller.admin;


import com.poscodx.popang.domain.dto.ProductCategoryDTO;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.service.CategoryService;
import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/category/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController {
    private final CategoryService categoryService;
    private static final int PAGE_SIZE = 10;

    @GetMapping("management")
    public String moveRegisterPage(Model model) {
        List<ProductCategoryDTO> list = categoryService.findAllByDepth(1L);
        model.addAttribute("largeCategories", list);
        return "product/admin/category_management";
    }
}
