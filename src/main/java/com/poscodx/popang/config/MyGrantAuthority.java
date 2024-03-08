package com.poscodx.popang.config;

import org.springframework.security.core.GrantedAuthority;

public class MyGrantAuthority implements GrantedAuthority {
    private final String authority;

    public MyGrantAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
