package com.example.spring_data.model.repository;

import com.example.spring_data.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
