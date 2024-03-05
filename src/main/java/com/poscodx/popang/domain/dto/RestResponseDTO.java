package com.poscodx.popang.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RestResponseDTO {
    private boolean success;
    private String message;
    private List<String> errors = new ArrayList<>();
}
