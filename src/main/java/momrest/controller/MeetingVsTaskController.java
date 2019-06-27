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

import momrest.model.MeetingVsTask;
import momrest.model.Meetings;
import momrest.model.User;
import momrest.service.IMeetingVsTaskService;

@RestController
@RequestMapping("mvt")
public class MeetingVsTaskController {
	
	@Autowired
    private CacheManager cacheManager;

	@Autowired
	private IMeetingVsTaskService mvtServ;
	
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
		try {
			flag=mvtServ.addMeetingVsTask(mvt);
			if(flag==false) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
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
}
