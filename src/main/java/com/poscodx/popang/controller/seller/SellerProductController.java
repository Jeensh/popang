package com.poscodx.popang.controller.seller;


import com.poscodx.popang.domain.ProductCategory;
import com.poscodx.popang.domain.User;
import com.poscodx.popang.domain.dto.ProductCategoryDTO;
import com.poscodx.popang.domain.dto.ProductDTO;
import com.poscodx.popang.domain.dto.RestResponseDTO;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.service.CategoryService;
import com.poscodx.popang.service.ProductService;
import com.poscodx.popang.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/seller/product/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class SellerProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private static final int PAGE_SIZE = 5;

    @GetMapping("{productId}")
    public String moveProductDetailPage(Model model, @PathVariable Long productId){
        ProductDTO product = productService.findById(productId);
        List<ProductCategoryDTO> list = categoryService.findAllByDepth(1L);



        // 분류 확인 메시지 만들기
        String categoryPath = categoryService.getCategoryPath(product.getCategoryCode());

        model.addAttribute("largeCategories", list);
        model.addAttribute("product", product);
        model.addAttribute("categoryPath", categoryPath);
        return "product/seller/edit_product";
    }

    @GetMapping("management")
    public String moveProductManagementPage(Authentication auth, Model model, @RequestParam(defaultValue = "0") int pageNumber) {
        UserDTO login = (UserDTO) auth.getPrincipal();
        String loginId = login.getId();

        if (pageNumber <= 0) pageNumber = 1;
        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
        Page<ProductDTO> productPage = productService.findAllBySeller(loginId, page);
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

        if (pageNumber > totalPage)
            return "redirect:/seller/product/management?pageNumber=" + totalPage;
        return "product/seller/product_management";
    }

    @GetMapping("add")
    public String moveToProductAddPage(Model model) {
        List<ProductCategoryDTO> list = categoryService.findAllByDepth(1L);
        model.addAttribute("largeCategories", list);
        return "product/seller/add_product";
    }

    @PostMapping("delete")
    @ResponseBody
    public RestResponseDTO deleteProduct(Authentication auth, Long id){
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        if(login.getRole() != 2)
            res.setSuccess(false);

        ProductDTO product = productService.findById(id);
        if(product.getId() == null)
            res.setSuccess(false);

        if(res.isSuccess()){
            productService.deleteById(id);
            res.setMessage(product.getName() + " 삭제 완료!");
        }
        else
            res.setMessage("상품 삭제 실패!");

        return res;
    }

    @PostMapping("edit")
    @ResponseBody
    public RestResponseDTO editProduct(Authentication auth, ProductDTO productDTO, @RequestParam(value = "images", defaultValue = "null") List<String> images, Model model) {
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        boolean canEdit = productValidation(productDTO, res, login);
        res.setSuccess(true);
        res.setMessage("상품 수정 성공!");

        if (canEdit) {
            productService.editProduct(productDTO, login.getId(), images);
        } else {
            res.setSuccess(false);
            StringBuilder sb = new StringBuilder();
            for (String m : res.getErrors()) sb.append(m).append("<br>");
            res.setMessage(sb.toString());
        }

        return res;
    }

    @PostMapping("add")
    @ResponseBody
    public RestResponseDTO addProduct(Authentication auth, ProductDTO productDTO, @RequestParam(value = "images", defaultValue = "null") List<String> images, Model model) {
        UserDTO login = (UserDTO) auth.getPrincipal();
        RestResponseDTO res = new RestResponseDTO();
        boolean canAdd = productValidation(productDTO, res, login);
        res.setSuccess(true);
        res.setMessage("상품 등록 성공!");

        System.out.println(productDTO);
        System.out.println(images);


        if (canAdd) {
            productService.addProduct(productDTO, login.getId(), images);
        } else {
            res.setSuccess(false);
            StringBuilder sb = new StringBuilder();
            for (String m : res.getErrors()) sb.append(m).append("<br>");
            res.setMessage(sb.toString());
        }

        return res;
    }

    private boolean productValidation(ProductDTO productDTO, RestResponseDTO res, UserDTO login) {
        int count = 0;
        boolean canAdd = true;
        List<String> messages = res.getErrors();

        if (login.getRole() != 2) {
            messages.add(++count + ". 판매자가 아니면 상품을 등록할 수 없습니다.");
            canAdd = false;
        }
        if (productDTO.getPrice() == null) {
            messages.add(++count + ". 상품 가격을 입력해주세요.");
            canAdd = false;
        }
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            messages.add(++count + ". 상품 이름을 입력해주세요.");
            canAdd = false;
        }
        if (productDTO.getStock() == null) {
            messages.add(++count + ". 상품 수량을 입력해주세요.");
            canAdd = false;
        }
        if (productDTO.getDescription() == null || productDTO.getDescription().isEmpty()) {
            messages.add(++count + ". 상품 설명을 입력해주세요.");
            canAdd = false;
        }
        if (productDTO.getDescriptionDetail() == null || productDTO.getDescriptionDetail().isEmpty()) {
            messages.add(++count + ". 상품 상세설명을 입력해주세요.");
            canAdd = false;
        }
        if (productDTO.getCategoryCode() == null) {
            messages.add(++count + ". 상품 카테고리가 잘못되었습니다.");
            canAdd = false;
        } else {
            ProductCategoryDTO category = categoryService.findByCode(productDTO.getCategoryCode());
            if (category == null) {
                messages.add(++count + ". 상품 카테고리가 잘못되었습니다.");
                canAdd = false;
            }
            if (category.getDepth() != 3) {
                messages.add(++count + ". 소분류 카테고리를 선택하세요.");
                canAdd = false;
            }
        }
        return canAdd;
    }
}
