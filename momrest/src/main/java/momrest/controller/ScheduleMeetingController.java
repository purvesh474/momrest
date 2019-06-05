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

import momrest.model.ScheduleMeetings;
import momrest.service.IScheduleMeetingService;

@RestController
@RequestMapping("meeting")
public class ScheduleMeetingController {

	@Autowired
	private IScheduleMeetingService meetingServ;
	
	@GetMapping("all")
	public ResponseEntity<List<ScheduleMeetings>> getAllMeetings(){
		List<ScheduleMeetings> meetingList=meetingServ.getAllMeeting();
		if(meetingList.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Meetings Found");
		}
		return new ResponseEntity<List<ScheduleMeetings>>(meetingList,HttpStatus.OK);
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<ScheduleMeetings> getMeetingById(@PathVariable(value="id") int id){
		
		ScheduleMeetings meeting = meetingServ.getMeetingById(id);
		
		if(meeting==null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Meeting Not Found");
		}
		return new ResponseEntity<ScheduleMeetings>(meeting,HttpStatus.OK);
	}
	
	
	@PostMapping("add")
	public ResponseEntity<Void> addMeeting(@RequestBody ScheduleMeetings meeting,UriComponentsBuilder builder){
		boolean flag=meetingServ.addMeeting(meeting);
		if(flag==false) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Something Went Wrong");
		}
		HttpHeaders headers=new HttpHeaders();
		headers.setLocation(builder.path("/id/{id}").buildAndExpand(meeting.getMeetingid()).toUri());
		return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
	}
	
	@PutMapping("update/{meetingid}")
	public ResponseEntity<ScheduleMeetings> updateMeeting(@PathVariable(value="meetingid") int meetingid,@RequestBody ScheduleMeetings meeting){
		try {
			meetingServ.updateMeeting(meeting, meetingid);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Something Went Wrong");
		}
		return new ResponseEntity<ScheduleMeetings>(meeting,HttpStatus.OK);
	}
	
}

