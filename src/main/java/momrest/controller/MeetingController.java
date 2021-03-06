package momrest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import momrest.model.Meetings;
import momrest.model.ParticipantsVsMeeting;
import momrest.model.User;
import momrest.service.IMeetingservice;
import momrest.service.IParticipantsVsMeetingService;
import momrest.service.IUserService;
import momrest.service.SendEmail;

@RestController
@RequestMapping("meeting")
public class MeetingController {

	@Autowired
	private IMeetingservice meetingServ;
	
	@Autowired
	private IParticipantsVsMeetingService pvmserv;
	
	
	@Autowired
	private IUserService userServ;
	
	
	@GetMapping("all")
	public ResponseEntity<List<Meetings>> getAllMeetings(){
		List<Meetings> meetingList=null;
		try {
			meetingList=meetingServ.getAllMeeting();
		if(meetingList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("Metting Size is " +meetingList.size());
		return new ResponseEntity<List<Meetings>>(meetingList,HttpStatus.OK);
	}
	
	@GetMapping("all/eid/{owner}")
	public ResponseEntity<List<Meetings>> getAllMeetings(@PathVariable(value="owner")String owner){
		List<Meetings> meetingList=null;
		try {
			meetingList=meetingServ.getAllUserMeeting(owner);
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
	public ResponseEntity<Integer> addMeeting(@RequestBody Meetings meeting,UriComponentsBuilder builder){
		int meetingId=-1;
		try {
			System.out.println("Meeting Object: "+meeting.toString());
		boolean flag=meetingServ.addMeeting(meeting);
		if(flag==true) {
			ParticipantsVsMeeting pvm=null;
		 meetingId=meetingServ.getLatestMeetingID();
		
		String participants=meeting.getParticipants();
		
		System.out.println("Partcipants name is "+participants);
		
		String[] partiCipantsArray=participants.split(",");
		
		for(String p : partiCipantsArray) {
			pvm=new ParticipantsVsMeeting();
			pvm.setMeetingid(meetingId);
			pvm.setUserid(p);
			pvm.setStatus(0);
			
			pvmserv.addPVM(pvm);
		}
		
		SendEmail sem = new SendEmail();
		sem.calenderInvite(meeting);
		sem.sendMail(participants, meeting.getSubject());
		}
		if(flag==false) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers=new HttpHeaders();
		headers.setLocation(builder.path("/id/{id}").buildAndExpand(meeting.getMeetingid()).toUri());
		return new ResponseEntity<Integer>(meetingId,HttpStatus.OK);
	}
	
	@PutMapping("update/{meetingid}")
	public ResponseEntity<Meetings> updateMeeting(@PathVariable(value="meetingid") int meetingid,@RequestBody Meetings meeting,@RequestBody HashMap<String, List<String>> updatePart){
		List<String> newParticipantList=new ArrayList<>();
		List<String> deleteParticipantList=new ArrayList<>();
		List<String> sameParticipantList=new ArrayList<>();
		
		newParticipantList=updatePart.get("addNewParticipants");
		deleteParticipantList=updatePart.get("deleteParticipants");
		sameParticipantList=updatePart.get("sameParticipants");
		
		StringBuilder Participants=new StringBuilder();
		
		if(deleteParticipantList!=null && deleteParticipantList.size()>0) {
		sameParticipantList.removeAll(deleteParticipantList);
		}
		
		
		if(newParticipantList!=null && newParticipantList.size()>0) {
			
			sameParticipantList.addAll(newParticipantList);
		}
		
		if(sameParticipantList!=null && sameParticipantList.size()>0) {
		for(int i=0;i<sameParticipantList.size();i++) {
			if(i<sameParticipantList.size()) {
			Participants.append(sameParticipantList.get(i));
			Participants.append(",");
			}else {
				Participants.append(sameParticipantList.get(i));
			}
		}
		}
		meeting.setParticipants(Participants.toString());
		ParticipantsVsMeeting pvm=null;
		int i=0;
		try {
			for(String s: deleteParticipantList) {
				
			i=pvmserv.deleteParticipants(s, meetingid);
			
			}
			meetingServ.updateMeeting(meeting, meetingid);
			
			
			for(String s : newParticipantList ) {
				pvm=new ParticipantsVsMeeting();
				pvm.setUserid(s);
				pvm.setMeetingid(meetingid);
				pvm.setStatus(0);
				pvmserv.addPVM(pvm);
				
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Meetings>(meeting,HttpStatus.OK);
	}
	
	
	@PutMapping("updateV1/{meetingid}")
	public ResponseEntity<Meetings> updateMeetingV1(@PathVariable(value="meetingid") int meetingid ,@RequestBody Meetings meeting){
		Meetings meetingupdated = null;
		try {
			meetingServ.updateMeeting(meeting, meetingid);
			meetingupdated = meetingServ.getMeetingById(meetingid);
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Meetings>(meetingupdated,HttpStatus.OK);
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
	
	@DeleteMapping("delete/{participant}/{meetingid}")
	public ResponseEntity<Integer> deleteParticipants(@PathVariable(value="participant") String participant ,@PathVariable(value="meetingid") int meetingid){
		int i=0;
		try {
			System.out.println("Called "+participant + " "+meetingid);
			
		i=pvmserv.deleteParticipants(participant,meetingid);
		}catch (Exception e) {
			System.out.println(e);
		}
		return new ResponseEntity<Integer>(i,HttpStatus.OK);
	}
	
	@GetMapping("meetingcountV1/{userid}")
	public ResponseEntity<Integer> getMeetingCountV1(@PathVariable(value="userid") String userid, @RequestParam(value="date", required = false) String date){
		int count=0;
		try {
			count=meetingServ.getMeetingCountV1(userid, date);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(count,HttpStatus.OK);
	}
	
	@GetMapping("getmeetinglistV1/{userid}")
	public ResponseEntity<List<Meetings>> getMeetingListV1(@PathVariable(value="userid") String userid, @RequestParam(value="date", required = false) String date){
		List<Meetings> meetingList=null;
		try {
			meetingList=meetingServ.getMeetingListsV1(userid, date);
			if(meetingList.isEmpty() || meetingList.size()==0) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Meetings>>(meetingList,HttpStatus.OK);
	}
	
}

