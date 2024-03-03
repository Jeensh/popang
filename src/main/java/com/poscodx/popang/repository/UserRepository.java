package com.poscodx.popang.repository;

import com.poscodx.popang.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserById(String id);
    Page<User> findAllUserByOrderBySignupDateDesc(Pageable pageable);

    void deleteUserById(String id);

}
