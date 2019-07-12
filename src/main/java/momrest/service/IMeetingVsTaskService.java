package momrest.service;

import java.util.List;

import momrest.model.MeetingVsTask;

public interface IMeetingVsTaskService {
	
	
	List<MeetingVsTask> getAllList();
	MeetingVsTask getMeetingVsTaskById(int taskid);
	boolean addMeetingVsTask(MeetingVsTask mvt);
	void updateMeetingBVsTask(MeetingVsTask mvt,int taskid);
	public int getLatesteInsertedValue();
}
