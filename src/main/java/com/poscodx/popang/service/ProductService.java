package com.poscodx.popang.service;

import com.poscodx.popang.domain.User;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.repository.ProductRepository;
import com.poscodx.popang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


}
