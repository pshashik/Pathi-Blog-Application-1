package com.pathi.blog.repository;

import com.pathi.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrUsername(String email, String username);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
