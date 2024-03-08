package com.poscodx.popang.controller;


import com.poscodx.popang.domain.dto.*;
import com.poscodx.popang.service.CategoryService;
import com.poscodx.popang.service.ProductService;
import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RequestMapping("/products")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private static final int PAGE_SIZE = 12;

    @PostMapping("/{productId}/view")
    public RestResponseDTO increaseProductView(Authentication auth, @PathVariable Long productId){
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        if(productService.findById(productId).getId() == null)
            res.setSuccess(false);
        if(login.getRole() != 1)
            res.setSuccess(false);
        if(res.isSuccess())
            productService.increaseProductView(productId);
        return res;
    }

    @GetMapping("/{productId}")
    public String moveProductDetailPage(Authentication auth, Model model, @PathVariable Long productId){
        UserDTO user = (UserDTO) auth.getPrincipal();
        ProductDTO product = productService.findById(productId);
        List<ProductCategoryDTO> list = categoryService.findAllByDepth(1L);

        // 분류 확인 메시지 만들기
        String categoryPath = categoryService.getCategoryPathWithNoCode(product.getCategoryCode());

        model.addAttribute("largeCategories", list);
        model.addAttribute("product", product);
        model.addAttribute("categoryPath", categoryPath);
        model.addAttribute("role", user.getRole());

        return "product/product_detail";
    }

    @GetMapping("")
    public String moveProductsPage(Authentication auth,
                                   Model model,
                                   @RequestParam(defaultValue = "0") int pageNumber,
                                   @RequestParam(defaultValue = "0") Long categoryCode,
                                   @RequestParam(defaultValue = "") String keyword) throws UnsupportedEncodingException {

        if (pageNumber <= 0) pageNumber = 1;
        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        Page<ProductDTO> productPage = productService.findAllByCategoryAndNameContaining(keyword, categoryCode, page);
        int totalPage = productPage.getTotalPages();
        long totalElement = productPage.getTotalElements();
        List<ProductDTO> productDTOList = productPage.getContent();

        int startPage = (((pageNumber - 1) / PAGE_SIZE) * 10) + 1;
        int endPage = Math.min(startPage + PAGE_SIZE - 1, totalPage);

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalElement", totalElement);
        model.addAttribute("productList", productDTOList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryCode", categoryCode);

        if (totalPage > 0 && pageNumber > totalPage) {
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            return "redirect:/products?pageNumber=" + totalPage + "&categoryCode=" + categoryCode + "&keyword=" + encodedKeyword;
        }
        System.out.println(totalPage);
        System.out.println(categoryCode);
        return "product/products";
    }
}
