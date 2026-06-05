package com.mundial.predictions.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mundial.predictions.model.User;
import com.mundial.predictions.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@GetMapping("/ranking")
	public List<User> orderUsersByScore() {
		return userService.orderUsersByScore();
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User currentUser) {

		Optional<User> user = userService.findByName(currentUser.getName(), currentUser.getPassword());

		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Integer id) {
		return userService.findUserById(id);
	}

}
