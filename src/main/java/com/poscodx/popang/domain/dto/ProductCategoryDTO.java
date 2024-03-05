package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductCategoryDTO {
    private Long code;
    private String name;
    private Long depth;

    private ProductCategory parentCategory;
    private List<ProductCategory> categoryList = new ArrayList<>();

    public void setDTOByEntity(ProductCategory category){
        code = category.getCode();
        name = category.getName();
        depth = category.getDepth();
    }
}