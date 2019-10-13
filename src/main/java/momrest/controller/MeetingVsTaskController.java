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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import momrest.model.MeetingVsTask;
import momrest.model.Meetings;
import momrest.model.ParticipantsVsTask;
import momrest.model.User;
import momrest.service.IMeetingVsTaskService;
import momrest.service.IParticipantsVsTaskService;

@RestController
@RequestMapping("mvt")
public class MeetingVsTaskController {
	
	@Autowired
    private CacheManager cacheManager;

	@Autowired
	private IMeetingVsTaskService mvtServ;
	
	@Autowired
	IParticipantsVsTaskService pvtServ;
	
	@GetMapping("all")
	public ResponseEntity<List<MeetingVsTask>> getAllMvt(){
		List<MeetingVsTask> mvtList=null;
		try {
			mvtList=mvtServ.getAllList();
			if(mvtList.isEmpty() || mvtList.size()==0) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<MeetingVsTask>>(mvtList,HttpStatus.OK);
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<MeetingVsTask> getMvtById(@PathVariable(value="id") int id){
		MeetingVsTask mvtObj=null;
		try {
			mvtObj=mvtServ.getMeetingVsTaskById(id);
			if(mvtObj==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<MeetingVsTask>(mvtObj,HttpStatus.OK);
	}
	
	@PostMapping("add")
	public ResponseEntity<Void> addMVT(@RequestBody MeetingVsTask mvt,UriComponentsBuilder builder){
		boolean flag=false;
		
		String[] ResponsibleArray=null;
		try {
			flag=mvtServ.addMeetingVsTask(mvt);
			if(flag==true) {
				ParticipantsVsTask pvt=null;
				int taskid=mvtServ.getLatesteInsertedValue();
				String Responsible=mvt.getResponsible();
				if(Responsible.contains(",")) {
					ResponsibleArray=Responsible.split(",");
				}
				if(ResponsibleArray.length>=1 && ResponsibleArray!=null) {
					for(String p : ResponsibleArray) {
						pvt=new ParticipantsVsTask();
						pvt.setTaskid(taskid);
						pvt.setUserid(p);
						pvt.setStatus(0);
						pvtServ.addPVT(pvt);
					}
				}else {
					pvt=new ParticipantsVsTask();
					pvt.setTaskid(taskid);
					pvt.setUserid(Responsible);
					pvt.setStatus(0);
					pvtServ.addPVT(pvt);
				}
				System.out.println("Latest Task id is "+taskid);
			}
			if(flag==false) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@PostMapping("addV1")
	public ResponseEntity<Void> addMVTV1(@RequestBody MeetingVsTask mvt,UriComponentsBuilder builder){
		
		try {
				mvtServ.addMeetingVsTask(mvt);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
		
	
	@PutMapping("update/{taskid}")
	public ResponseEntity<MeetingVsTask> updateMVT(@PathVariable(value="taskid") int taskid ,@RequestBody MeetingVsTask mvt){
		
		try {
			mvtServ.updateMeetingBVsTask(mvt, taskid);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<MeetingVsTask>(mvt,HttpStatus.OK);
	}
	
	@GetMapping("taskcountV1/{userid}")
	public ResponseEntity<Integer> getTaskCountV1(@PathVariable(value="userid") String userid, @RequestParam(value="tasktype", required = false) String tasktype, @RequestParam(value="date", required = false) String date){
		int count=0;
		try {
			count=mvtServ.getTaskCountV1(userid, tasktype, date);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(count,HttpStatus.OK);
	}
	
	@GetMapping("gettasklistV1/{userid}")
	public ResponseEntity<List<MeetingVsTask>> getTaskListV1(@PathVariable(value="userid") String userid, @RequestParam(value="tasktype", required = false) String tasktype, @RequestParam(value="date", required = false) String date){
		List<MeetingVsTask> mvtList=null;
		try {
			mvtList=mvtServ.getTaskListsV1(userid, tasktype, date);
			if(mvtList.isEmpty() || mvtList.size()==0) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<MeetingVsTask>>(mvtList,HttpStatus.OK);
	}
	
	
	@GetMapping("getMeetVsTaskByMeetingId/{meetingId}")
	public ResponseEntity<List<MeetingVsTask>> getMeetVsTaskByMeetingId(@PathVariable(value="meetingId") String meetingId){
		List<MeetingVsTask> mvtList=null;
		try {
			mvtList=mvtServ.getMeetVsTaskByMeetingId(Integer.parseInt(meetingId));
			if(mvtList.isEmpty() || mvtList.size()==0) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<MeetingVsTask>>(mvtList,HttpStatus.OK);
	}
	
	
	@GetMapping("taskcount/{userid}")
	public ResponseEntity<Integer> getTaskCount(@PathVariable(value="userid") String userid){
		int count=0;
		try {
			count=pvtServ.getTaskCount(userid);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(count,HttpStatus.OK);
	}
}
