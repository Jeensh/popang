package com.poscodx.popang.controller.admin;


import com.poscodx.popang.domain.Banner;
import com.poscodx.popang.domain.dto.BannerDTO;
import com.poscodx.popang.domain.dto.ProductCategoryDTO;
import com.poscodx.popang.domain.dto.RestResponseDTO;
import com.poscodx.popang.service.BannerService;
import com.poscodx.popang.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/banner/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminBannerController {
    private final BannerService bannerService;
    @GetMapping("management")
    public String moveRegisterPage(Model model) {
        List<BannerDTO> list = bannerService.findAll();
        model.addAttribute("bannerList", list);
        return "banner/banner_management";
    }

    @PostMapping("add")
    public String addBanner(String address) {
        bannerService.addBanner(address);
        return "redirect:/admin/banner/management";
    }

    @PostMapping("delete")
    @ResponseBody
    public RestResponseDTO deleteBanner(Long id) {
        bannerService.deleteBanner(id);
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        res.setMessage(id + "번 항목 삭제 완료");
        return res;
    }
}
