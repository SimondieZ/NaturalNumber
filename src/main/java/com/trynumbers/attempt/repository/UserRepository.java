package com.trynumbers.attempt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trynumbers.attempt.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
