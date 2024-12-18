package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   
}