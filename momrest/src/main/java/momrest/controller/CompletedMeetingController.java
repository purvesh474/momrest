package momrest.controller;

import java.util.List;

import javax.annotation.PostConstruct;

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

import momrest.model.CompletedMeeting;
import momrest.model.ScheduleMeetings;
import momrest.service.ICompletedMeetingService;

@RestController
@RequestMapping("compMeeting")
public class CompletedMeetingController {

	@Autowired
	private ICompletedMeetingService cmetingserv;
	
	@GetMapping("all")
	public ResponseEntity<List<CompletedMeeting>> getAllCompletedMeeting(){
		
		List<CompletedMeeting> CMList=cmetingserv.getAllCompletedMeeting();
		if(CMList.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Completed Meeting not found!");
		}
		return new ResponseEntity<List<CompletedMeeting>>(CMList,HttpStatus.OK);
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<CompletedMeeting> getCompletedMeetingById(@PathVariable(value="id") int id){
		CompletedMeeting CMObj=cmetingserv.getCompletedMetingById(id);
		if(CMObj==null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Completed Meeting not found!");
		}
		return new ResponseEntity<CompletedMeeting>(CMObj,HttpStatus.OK);
	}
	
	@PostMapping("add")
	public ResponseEntity<Void> addCompletedMeeting(@RequestBody CompletedMeeting cmeeting,UriComponentsBuilder builder){
		
		boolean flag=cmetingserv.addCompletedMeeting(cmeeting);
		if(flag==false) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Something Went Wrong!");
		}
		HttpHeaders headers=new HttpHeaders();
		headers.setLocation(builder.path("/id/{id}").buildAndExpand(cmeeting.getId()).toUri());
		return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<CompletedMeeting> updateCompletedMeeting(@RequestBody CompletedMeeting cmeeting,@PathVariable(value="id") int id){
		try {
			cmetingserv.updateCompletedMeeting(cmeeting, id);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Something Went Wrong");
		}
		return new ResponseEntity<CompletedMeeting>(cmeeting,HttpStatus.OK);
	}
}
