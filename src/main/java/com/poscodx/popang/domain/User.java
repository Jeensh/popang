package com.poscodx.popang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String password;
    private String tel;
    private String name;
    private Long role;
    private Long grade;
    @Column(name = "signup_date")
    private Timestamp signupDate;

    @OneToMany(mappedBy = "user")
    List<Address> addressList = new ArrayList<>();

    @OneToMany(mappedBy = "constructor")
    List<Coupon> constructedCoupons = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Orders> ordersList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Delivery> deliveryList = new ArrayList<>();
}
