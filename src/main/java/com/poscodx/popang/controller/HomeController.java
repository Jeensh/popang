package com.poscodx.popang.controller;

import com.poscodx.popang.domain.ProductCategory;
import com.poscodx.popang.domain.dto.BannerDTO;
import com.poscodx.popang.domain.dto.ProductCategoryDTO;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.repository.CategoryRepository;
import com.poscodx.popang.service.BannerService;
import com.poscodx.popang.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BannerService bannerService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String moveLoginPage(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "index";
    }

    @GetMapping("/main")
    public String moveMainPage(Authentication auth, Model model) {
        UserDTO login = (UserDTO) auth.getPrincipal();

        String role = login.getRole().toString();
        switch (role) {
            case "3": {
                return "main/admin_main";
            }
            case "2": {
                return "main/seller_main";
            }
            default: {
                List<ProductCategory> largeCategories = categoryService.findLargeForMenu();
                model.addAttribute("largeCategories", largeCategories);
                List<BannerDTO> bannerList = bannerService.findAll();
                model.addAttribute("bannerList", bannerList);
                return "main/main";
            }
        }
    }
}
