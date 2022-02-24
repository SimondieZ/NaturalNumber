package com.simondiez.springnumbers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simondiez.springnumbers.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
