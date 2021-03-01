package com.example.demo.repository;

import com.example.demo.repository.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, String> {
    User findByUsername(String username);
}
