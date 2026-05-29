package com.mundial.predictions.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mundial.predictions.model.User;
import com.mundial.predictions.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public List<User> orderUsersByScore() {
		return userRepository.findAllByOrderByTotalScoreDesc();

	}

}
