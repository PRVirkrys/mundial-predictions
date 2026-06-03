package com.mundial.predictions.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundial.predictions.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findAllByOrderByTotalScoreDesc();

	Optional<User> findByNameAndPassword(String name, String password);

}
