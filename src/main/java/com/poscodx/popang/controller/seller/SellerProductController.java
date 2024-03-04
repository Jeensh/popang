package com.poscodx.popang.controller.seller;


import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.service.ProductService;
import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/seller/products/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class SellerProductController {
    private final ProductService productService;
    private static final int PAGE_SIZE = 10;

//    @GetMapping("management")
//    public String moveRegisterPage(Model model, @RequestParam(defaultValue = "0") int pageNumber) {
//        if(pageNumber <= 0) pageNumber = 1;
//        PageRequest page = PageRequest.of(pageNumber - 1, PAGE_SIZE);
//        Page<UserDTO> userPage = userService.findAllByPage(page);
//        int totalPage = userPage.getTotalPages();
//        long totalElement = userPage.getTotalElements();
//        List<UserDTO> userDTOList = userPage.getContent();
//
//        int startPage = (((pageNumber - 1) / PAGE_SIZE) * 10) + 1;
//        int endPage = Math.min(startPage + PAGE_SIZE - 1, totalPage);
//
//        model.addAttribute("totalPage", totalPage);
//        model.addAttribute("totalElement", totalElement);
//        model.addAttribute("userList", userDTOList);
//        model.addAttribute("currentPage", pageNumber);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
//        if(pageNumber > totalPage)
//            return "redirect:/admin/user/management?pageNumber=" + totalPage;
//        return "user/admin/user_management";
//    }
}
