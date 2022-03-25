package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired UserRepo userRepo;

	public User getUserEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	
	public User getUsername(String username) {
		return userRepo.findByUsername(username);
	}


	public List<User> getAllUsers() {
		return userRepo.findAllUsers();
	}
	
	

}
