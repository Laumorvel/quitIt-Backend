package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MeetUp;
import com.example.demo.model.User;
import com.example.demo.repository.MeetUpRepo;

@Service
public class MeetUpService {
	
	@Autowired MeetUpRepo meetUpRepo;

	public List<MeetUp> getAllMeetUps() {
		return meetUpRepo.findAllMeetUps();
	}

}
