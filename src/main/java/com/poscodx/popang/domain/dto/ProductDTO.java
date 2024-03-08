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
    private Long categoryCode;
    private String sellerId;
    private String sellerName;
    private Long view;
    private Long sales;
    private Double score;

    private List<ReplyDTO> replyList = new ArrayList<>();
    private List<ProductImage> imageList = new ArrayList<>();

    public void setDTOByEntity(Product product){
        id = product.getId();
        name = product.getName();
        price = product.getPrice();
        view = product.getView();
        sales = product.getSales();
        score = product.getScore();
        stock = product.getStock();
        description = product.getDescription();
        descriptionDetail = product.getDescriptionDetail();
        uploadDate = product.getUploadDate();
    }
}

