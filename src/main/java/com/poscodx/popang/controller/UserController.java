package com.poscodx.popang.controller;


import com.poscodx.popang.domain.dto.AuthDTO;
import com.poscodx.popang.domain.dto.ProductDTO;
import com.poscodx.popang.domain.dto.RestResponseDTO;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("data")
    @ResponseBody
    public AuthDTO auth(Authentication auth){
        UserDTO login = (UserDTO) auth.getPrincipal();
        AuthDTO authDTO = new AuthDTO();
        authDTO.setId(login.getId());
        authDTO.setName(login.getName());
        authDTO.setRole(login.getRole());
        authDTO.setGrade(login.getGrade());
        return authDTO;
    }

    @GetMapping("register")
    public String moveRegisterPage() {
        return "user/register";
    }

    @PostMapping("register")
    public String register(Model model, UserDTO userDTO) {

        if (registerValidation(userDTO, model)) {
            userService.register(userDTO);
            return "redirect:/";
        }
        else
            return "user/register";
    }

    public boolean registerValidation(UserDTO userDTO, Model model) {
        log.info("register : {}", userDTO);
        List<String> messages = new ArrayList<>();
        messages.add("회원가입에 실패하였습니다.\n");
        int count = 1;
        // id 검증
        if (userDTO.getId().isEmpty())
            messages.add(count++ + ". " + "아이디를 입력해주세요\n");
        if (((UserDTO)userService.loadUserByUsername(userDTO.getId())).getId() != null)
            messages.add(count++ + ". " + "이미 존재하는 아이디입니다.\n");
        // pw 검증
        if (userDTO.getPassword().isEmpty())
            messages.add(count++ + ". " + "비밀번호를 입력해주세요\n");
        if (!userDTO.getPassword().equals(userDTO.getPasswordCheck()))
            messages.add(count++ + ". " + "패스워드와 패스워드 확인이 다릅니다.\n");
        // tel 검증
        if (userDTO.getTel().length() > 11)
            messages.add(count++ + ". " + "전화번호는 11자 이하로 작성해주세요.\n");
        // role 검증
        if (!(userDTO.getRole() == null || userDTO.getRole() == 2L))
            messages.add(count + ". " + "구매자와 판매자 이외의 역할로 회원가입을 할 수 없습니다.\n");

        if (messages.size() == 1) {
            userDTO.setGrade(1L);
            return true;
        }
        else {
            model.addAttribute("messages", messages);
            return false;
        }
    }

//    테스트 데이터용
//    @GetMapping("test")
//    public String test(){
//        for(int i = 1; i <= 300; i++){
//            UserDTO user = new UserDTO();
//            user.setId(String.valueOf(i));
//            user.setPassword("1234");
//            user.setGrade(1L);
//            user.setName(i + "번유저");
//            user.setRole(1L);
//            user.setTel("01012345678");
//            userService.register(user);
//        }
//        return "redirect:/main";
//    }
}
