package com.yhao.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yhao.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserName(String userName);
	User findByUserNameOrEmail(String username, String email);
}
