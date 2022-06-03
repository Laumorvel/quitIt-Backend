package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.AlreadySetAsAnSmokingDayException;
import com.example.demo.error.ApiError;
import com.example.demo.error.MeetUpException;
import com.example.demo.error.UserNotAttendanceException;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.error.UserRepeatException;
import com.example.demo.model.MeetUp;
import com.example.demo.model.User;
import com.example.demo.repository.MeetUpRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.MeetUpService;

@RestController
public class MeetUpController {
	
	@Autowired
	private MeetUpService meetUpService;
	
	@Autowired
	private MeetUpRepo meetUpRepo;
	
	@Autowired
	private UserRepo userRepo;

	/**
	 * Da la lista de meet ups
	 * 
	 * @return
	 */
	@GetMapping("/meetUps")
	public List<MeetUp> getAllMeetUps() {
		return meetUpService.getAllMeetUps();
	}
	
	@GetMapping("/meetUp")
	public List<MeetUp> getAllMeetUpsUser(@RequestParam (required = false) String choice) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);
		
		if (result == null) {
			throw new UserNotFoundException();
		} else if (choice.equals("true")) {
			return meetUpService.getAllMeetUpsUserAttendace(result);
		}
		else {
			return meetUpService.getAllMeetUpsUserNotAttendace(result);
		}
		
		
	}
	
	
	@PostMapping("/meetUp")
	public MeetUp createMeetUp(@RequestBody MeetUp meet) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);
		
		if(result==null) {
			throw new UserNotFoundException();
		}
			 return this.meetUpService.createMeetUp(meet);
		}

	

	@PostMapping("/meetUp/{id}")
	public MeetUp addAttendace(@PathVariable Long id, @RequestParam (required = false) String choice) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User result = userRepo.findByEmail(email);
		
		if(result==null) {
			throw new UserNotFoundException();
		}
		else {	
			System.out.println(choice);
			if(choice.equals("true")) {
				return this.meetUpService.addAttendace(result, id);
			}
			else {
				return this.meetUpService.deletetAttendace(result, id);
			}
			
			}
		}


	@ExceptionHandler(UserRepeatException.class)
	public ResponseEntity<ApiError> alreadySetAsAnSmokingDayException(UserRepeatException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}

	@ExceptionHandler(UserNotAttendanceException.class)
	public ResponseEntity<ApiError> alreadySetAsAnSmokingDayException(UserNotAttendanceException ex)
			throws Exception {
		ApiError e = new ApiError();
		e.setEstado(HttpStatus.CONFLICT);
		e.setMensaje(ex.getMessage());
		e.setFecha(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
	}

	
	
	
	}
	
	



