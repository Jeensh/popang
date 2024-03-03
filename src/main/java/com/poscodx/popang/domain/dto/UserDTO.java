package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.*;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDTO implements UserDetails {
    private String id;
    private String password;
    private String passwordCheck;
    private String tel;
    private String name;
    private Long role;
    private Long grade;
    private Timestamp signupDate;
    List<Address> addressList = new ArrayList<>();
    List<Coupon> constructedCoupons = new ArrayList<>();
    List<Coupon> coupons = new ArrayList<>();
    List<Orders> ordersList = new ArrayList<>();
    List<Delivery> deliveryList = new ArrayList<>();

    public void setByUserEntity(User user){
        id = user.getId();
        password = user.getPassword();
        tel = user.getTel();
        name = user.getName();
        role = user.getRole();
        grade = user.getGrade();
        signupDate = user.getSignupDate();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
