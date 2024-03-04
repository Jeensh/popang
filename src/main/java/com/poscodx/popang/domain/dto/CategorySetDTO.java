package com.poscodx.popang.domain.dto;

import lombok.Data;

@Data
public class CategorySetDTO {
    String largeName = "대분류(미정)";
    String mediumName = "중분류(미정)";
    String smallName = "소분류(미정)";
    Long largeCode;
    Long mediumCode;
    Long smallCode;
}
