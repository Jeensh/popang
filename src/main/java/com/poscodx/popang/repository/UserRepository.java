package com.poscodx.popang.repository;

import com.poscodx.popang.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findUserById(String id);
}
