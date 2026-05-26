package com.anvesh.finance_manager.repository;

import com.anvesh.finance_manager.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    // FIND USER BY EMAIL
    Optional<User> findByEmail(
            String email
    );

    // CHECK EMAIL EXISTS
    boolean existsByEmail(
            String email
    );
}