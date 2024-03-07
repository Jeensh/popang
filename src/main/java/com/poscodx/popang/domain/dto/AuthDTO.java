package com.poscodx.popang.domain.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String id;
    private Long role;
    private String name;
    private Long grade;
}
