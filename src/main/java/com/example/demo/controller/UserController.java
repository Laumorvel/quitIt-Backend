package com.example.demo.controller;


import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.IncidenceNotExist;
import com.example.demo.error.UserNotFoundException;
import com.example.demo.model.Achievement;
import com.example.demo.model.CommentCommunity;
import com.example.demo.model.Incidence;
import com.example.demo.model.MeetUp;
import com.example.demo.model.Message;
import com.example.demo.model.Penalty;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.AchievementService;
import com.example.demo.service.CommentsCommunityService;
import com.example.demo.service.IncidenceService;
import com.example.demo.service.MeetUpService;
import com.example.demo.service.PenaltyService;
import com.example.demo.service.SmtpMailSender;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	
	 @Autowired private UserRepo userRepo;
	 
	 @Autowired private UserService userService;
	 
	 @Autowired private CommentsCommunityService commentsCommunityService;
	 
	 @Autowired private IncidenceService incidenceService;
	 
	 @Autowired private MeetUpService meetUpService;
	 
	 @Autowired private AchievementService achievementService;
	 
	 @Autowired private PenaltyService penaltyService;
	 
	 @Autowired private SmtpMailSender smtpMailSender;
	    
	  
	
	@GetMapping("/user")
    public ResponseEntity<User> getUser() {
    	
    	String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User result =  userRepo.findByEmail(email);
        
    	if(result==null) {
    		throw new UserNotFoundException();
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.OK).body(result);
    	}
     	
	}
	
	
	 @GetMapping("/email")
	 public User checkEmailUsers(@RequestParam(required = false) String email, @RequestParam(required = false) String username) {
			if (username == null) {
				return userService.getUserEmail(email);
			} else {
				return userService.getUsername(username);
			}
		}



	 @GetMapping("/commentsCommunity")
	    public List<CommentCommunity> getComments() {
	    	return commentsCommunityService.getComments();
	     	
		}
	 
	 @GetMapping("/commentsCommunity/{idC}")
	    public CommentCommunity getCommentById(@PathVariable Long idC) {
		 CommentCommunity comment= incidenceService.getCommentById(idC);
			
			if (comment == null) {
				throw new IncidenceNotExist((long)idC);
			} else {
				return comment;
			}
	     	
		}
	 
	 @PostMapping("/commentsCommunity")
	    public CommentCommunity addCommentsCommunity(@RequestBody CommentCommunity datos) {
	    	
	    	String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User result =  userRepo.findByEmail(email);
	   
	    	if(result==null) {
				throw new UserNotFoundException();
			}
			else {
				return this.commentsCommunityService.addCommentCommunity(result, datos);
			}
		}
	
	 @GetMapping("/incidence")
	    public List<Incidence> getAllIncidences() {
	    	return incidenceService.getAllIncidences();
		}
	 
	 @PostMapping("/incidence")
	    public Incidence createIncidence(@RequestBody Incidence datos) {
	    	
	    	String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User result =  userRepo.findByEmail(email);
	   
	    	if(result==null) {
				throw new UserNotFoundException();
			}
			else {
				return this.incidenceService.createIncidence(result, datos);
			}
		}
	
	 
	 @PutMapping("/incidence/{idi}")
		public Incidence editIncidence(@PathVariable Long idi, @RequestBody CommentCommunity comentario) {
		 
		 String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     User result =  userRepo.findByEmail(email);
	     
	     if(result==null) {
				throw new UserNotFoundException();
			}
			else {
				Incidence incidence= incidenceService.editIncidence(idi, comentario);
				
				if (incidence == null) {
					throw new IncidenceNotExist(idi);
				} else {
					return incidence;
				}
			}
		}
	 
	
	 
	 @GetMapping("/users")
	    public List<User> getAllUsers() {
	    	return userService.getAllUsers();
		}
	 
	 
	 @GetMapping("/meetUp")
	    public List<MeetUp> getAllMeetUps() {
	    	return meetUpService.getAllMeetUps(); 	
		}
	 
	 @GetMapping("/achievement")
	    public List<Achievement> getAllAchievement() {
	    	return achievementService.getAllAchievement(); 	
		}
	 
	 @GetMapping("/penalty")
	    public List<Penalty> getAllPenalty() {
	    	return penaltyService.getAllPenalty(); 	
		}
	 
	 
	 
	
	@PostMapping("/mail")
    public void sendEmail(@RequestBody Message datos) throws MessagingException {
    	datos.setToUser("aalira.96@gmail.com");
    	
		smtpMailSender.send(datos.getToUser(), datos.getSubject(), datos.getText(), datos.getFromUser());
	} 
	
	
	
	
}
