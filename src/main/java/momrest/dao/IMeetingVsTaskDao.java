package momrest.dao;

import java.util.List;

import momrest.model.MeetingVsTask;

public interface IMeetingVsTaskDao {
	
	List<MeetingVsTask> getAllMVT();
	MeetingVsTask getMVTById(int taskid);
	void addMeetingVsTask(MeetingVsTask MVT);
	void updateMeetingVsTask(MeetingVsTask MVT,int taskid);
	public List<MeetingVsTask> getMeetVsTaskByMeetingId(int meetingId);
	public int getLatesteInsertedValue();
	public int getTaskCountV1(String userid, String tasktype, String date);
	public List<MeetingVsTask> getTaskListsV1(String userid, String tasktype, String date);
	
}
