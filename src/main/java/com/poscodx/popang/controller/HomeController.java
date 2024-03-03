package com.poscodx.popang.controller;

import com.poscodx.popang.domain.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String moveLoginPage(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "index";
    }

    @GetMapping("/main")
    public String moveMainPage(Authentication auth) {
        UserDTO login = (UserDTO) auth.getPrincipal();

        String role = login.getRole().toString();
        return switch (role) {
            case "3" -> "main/admin_main";
            case "2" -> "main/seller_main";
            default -> "main/main";
        };
    }
}
