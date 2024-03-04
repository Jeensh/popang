package com.poscodx.popang.domain;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product_category")
public class ProductCategory {
    @Id
    private Long code;
    private String name;
    private Long depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code")
    private ProductCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<ProductCategory> categoryList = new ArrayList<>();
}