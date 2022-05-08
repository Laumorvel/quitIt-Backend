package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;

@RestController
public class FriendsController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;

	/**
	 * Da la lista de amigos de un usuario
	 * 
	 * @return
	 */
	@GetMapping("/friend")
	public List<User> getAllfriend() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);

		if (result == null) {
			throw new UserNotFoundException();
		} else {
			return userService.getAllFriends(result);
		}

	}

}
