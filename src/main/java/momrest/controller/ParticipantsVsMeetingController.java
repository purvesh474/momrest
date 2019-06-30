package momrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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
import org.springframework.web.util.UriComponentsBuilder;

import momrest.model.ParticipantsVsMeeting;
import momrest.model.User;
import momrest.service.IParticipantsVsMeetingService;
import momrest.service.IUserService;

@RestController
@RequestMapping("pvm")
public class ParticipantsVsMeetingController {

	@Autowired
    private CacheManager cacheManager;
	
	@Autowired
	private IParticipantsVsMeetingService pvmserv;
	
	
	
	@PostMapping("add")
	public ResponseEntity<Void> addPVM(@RequestBody ParticipantsVsMeeting pvm,UriComponentsBuilder builder){
		boolean flag=false;
		try {
			flag=pvmserv.addPVM(pvm);
			if(flag==false) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("update")
	public ResponseEntity<ParticipantsVsMeeting> updatePVM(@RequestBody ParticipantsVsMeeting pvm){
		
		try {
			pvmserv.updatePVM(pvm);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		return new ResponseEntity<ParticipantsVsMeeting>(pvm,HttpStatus.OK);
	}
	
	@GetMapping("all/uid/{userid}")
	public ResponseEntity<List<ParticipantsVsMeeting>> getAllPVMByUserId(@PathVariable(value="userid") int userid){
		List<ParticipantsVsMeeting> pvmList=null;
		try {
			pvmList=pvmserv.getAllPVMByUserid(userid);
			if(pvmList.size()==0 || pvmList==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<ParticipantsVsMeeting>>(pvmList,HttpStatus.OK);
	}
	
	@GetMapping("meetingcount/{userid}")
	public ResponseEntity<Integer> getMeetingCount(@PathVariable(value="userid") String userid){
		int count=0;
		try {
			count=pvmserv.getMeetingCount(userid);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(count,HttpStatus.OK);
	}
	
	@GetMapping("all/mid/{meetingid}")
	public ResponseEntity<List<ParticipantsVsMeeting>> getAllPVMByMeetingId(@PathVariable(value="meetingid") int meetingid){
		List<ParticipantsVsMeeting> pvmList=null;
		try {
			pvmList=pvmserv.getAllPVMByMeetingId(meetingid);
			if(pvmList.size()==0 || pvmList==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<List<ParticipantsVsMeeting>>(pvmList,HttpStatus.OK);
	}
}
