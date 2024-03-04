package com.poscodx.popang.controller.rest;


import com.poscodx.popang.domain.dto.RestResponseDTO;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/rest/users/")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RestUserController {
    private final UserService userService;

    @RequestMapping("update")
    public RestResponseDTO changeGrade(String id, Long grade){
        UserDTO userToReset = (UserDTO) userService.loadUserByUsername(id);
        Long originGrade = userToReset.getGrade();
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        if(userToReset.getId().isEmpty()){
            res.setMessage("찾을 수 없는 아이디입니다.");
            res.setSuccess(false);
        }
        else {
            userService.changeGrade(id, grade);
            res.setMessage(getGrade(originGrade) + " => " + getGrade(grade));
        }
        return res;
    }

    @RequestMapping("reset")
    public RestResponseDTO resetPassword(String id){
        UserDTO userToReset = (UserDTO) userService.loadUserByUsername(id);
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        if(userToReset.getId().isEmpty()){
            res.setMessage("찾을 수 없는 아이디입니다.");
            res.setSuccess(false);
        }
        else if(userToReset.getRole() == 3){
            res.setMessage("관리자는 초기화 할 수 없습니다.");
            res.setSuccess(false);
        }
        else {
            userService.resetPassword(id);
            res.setMessage("초기화된 비밀번호 : 1234");
        }
        return res;
    }

    @RequestMapping("delete")
    public RestResponseDTO deleteUserById(String id){
        UserDTO userToDelete = (UserDTO) userService.loadUserByUsername(id);
        RestResponseDTO res = new RestResponseDTO();
        res.setSuccess(true);
        if(userToDelete.getId().isEmpty()){
            res.setMessage("찾을 수 없는 아이디입니다.");
            res.setSuccess(false);
        }
        else if(userToDelete.getRole() == 3){
            res.setMessage("관리자는 삭제할 수 없습니다.");
            res.setSuccess(false);
        }
        else {
            userService.deleteUserById(id);
            res.setMessage(userToDelete.getId() + " 삭제");
        }
        return res;
    }

    private String getGrade(Long grade){
        return switch (String.valueOf(grade)) {
            case "1" -> "BRONZE";
            case "2" -> "SILVER";
            case "3" -> "GOLD";
            case "4" -> "VIP";
            default -> "UNDEFINED";
        };
    }
}
