package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MeetUp;
import com.example.demo.service.MeetUpService;

@RestController
public class MeetUpController {
	@Autowired
	private MeetUpService meetUpService;

	/**
	 * Da la lista de meet ups
	 * 
	 * @return
	 */
	@GetMapping("/meetUp")
	public List<MeetUp> getAllMeetUps() {
		return meetUpService.getAllMeetUps();
	}
	


}
