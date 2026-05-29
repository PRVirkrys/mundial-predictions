package com.mundial.predictions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundial.predictions.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
