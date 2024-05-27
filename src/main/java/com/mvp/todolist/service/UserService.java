package com.mvp.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mvp.todolist.entities.User;
import com.mvp.todolist.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
}
