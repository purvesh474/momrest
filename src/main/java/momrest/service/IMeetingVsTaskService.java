package momrest.service;

import java.util.List;

import momrest.model.MeetingVsTask;

public interface IMeetingVsTaskService {
	
	
	List<MeetingVsTask> getAllList();
	MeetingVsTask getMeetingVsTaskById(int taskid);
	boolean addMeetingVsTask(MeetingVsTask mvt);
	void updateMeetingBVsTask(MeetingVsTask mvt,int taskid);
	public List<MeetingVsTask> getMeetVsTaskByMeetingId(int meetingId);
	public int getLatesteInsertedValue();
	public int getTaskCountV1(String userid, String tasktype, String date);
	public List<MeetingVsTask> getTaskListsV1(String userid, String tasktype, String date);
	
}
