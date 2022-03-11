package com.egongil.numva.core.repository;

import com.egongil.numva.core.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneAndName(String phone, String name);
}
