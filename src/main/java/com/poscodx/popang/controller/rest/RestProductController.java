package com.poscodx.popang.controller.rest;


import com.poscodx.popang.domain.dto.RestResponseDTO;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.service.ProductService;
import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/products/")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RestProductController {
    private final ProductService productService;

}
