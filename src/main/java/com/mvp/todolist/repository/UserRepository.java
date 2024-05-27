package com.mvp.todolist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvp.todolist.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmail(String login);

}