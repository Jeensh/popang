package com.poscodx.popang.domain.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String id;
    private Long role;
    private String name;
    private Long grade;

    public void setByUserDTO(UserDTO userDTO){
        id = userDTO.getId();
        role = userDTO.getRole();
        name = userDTO.getName();
        grade = userDTO.getGrade();
    }
}
