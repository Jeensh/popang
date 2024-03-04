package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.ProductImage;
import com.poscodx.popang.domain.Reply;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Long price;
    private Long stock;
    private String description;
    private String descriptionDetail;
    private Timestamp uploadDate;

    private List<Reply> replyList = new ArrayList<>();
    private List<ProductImage> imageList = new ArrayList<>();

    public void setDTOByEntity(Product product){
        id = product.getId();
        name = product.getName();
        price = product.getPrice();
        stock = product.getStock();
        description = product.getDescription();
        descriptionDetail = product.getDescriptionDetail();
        uploadDate = product.getUploadDate();
    }
}

