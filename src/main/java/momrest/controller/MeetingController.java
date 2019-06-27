package momrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import momrest.model.Meetings;
import momrest.model.User;
import momrest.service.IMeetingservice;
import momrest.service.IUserService;

@RestController
@RequestMapping("meeting")
public class MeetingController {

	@Autowired
	private IMeetingservice meetingServ;
	
	@Autowired
	private IUserService userServ;
	
	@GetMapping("all/{owner}")
	public ResponseEntity<List<Meetings>> getAllMeetings(@PathVariable(value="owner")String owner){
		List<Meetings> meetingList=null;
		try {
			meetingList=meetingServ.getAllMeeting(owner);
		if(meetingList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("Metting Size is " +meetingList.size());
		return new ResponseEntity<List<Meetings>>(meetingList,HttpStatus.OK);
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<Meetings> getMeetingById(@PathVariable(value="id") int id){
		Meetings meeting=null;
		try {
			meeting = meetingServ.getMeetingById(id);
		
		if(meeting==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Meetings>(meeting,HttpStatus.OK);
	}
	
	
	@PostMapping("add")
	public ResponseEntity<Void> addMeeting(@RequestBody Meetings meeting,UriComponentsBuilder builder){
		try {
		boolean flag=meetingServ.addMeeting(meeting);
		if(flag==false) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers=new HttpHeaders();
		headers.setLocation(builder.path("/id/{id}").buildAndExpand(meeting.getMeetingid()).toUri());
		return new ResponseEntity<Void>(headers,HttpStatus.OK);
	}
	
	@PutMapping("update/{meetingid}")
	public ResponseEntity<Meetings> updateMeeting(@PathVariable(value="meetingid") int meetingid,@RequestBody Meetings meeting){
		try {
			meetingServ.updateMeeting(meeting, meetingid);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Meetings>(meeting,HttpStatus.OK);
	}
	
	@GetMapping("search/{email}")
	public ResponseEntity<List<User>> searchEmailUser(@PathVariable(value="email") String email ){
		//System.out.println("Entered in Search method"+email);
		List<User> userList=null;
		try {
			userList=userServ.searchUserEmail(email);
			if(userList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
	}
	
}

