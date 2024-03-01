package com.poscodx.popang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long price;
    private Long stock;
    private String description;
    private String descriptionDetail;
    private Date uploadDate;

    @OneToMany(mappedBy = "product")
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductImage> imageList = new ArrayList<>();
}
