package momrest.dao;

import java.util.List;

import momrest.model.MeetingVsTask;

public interface IMeetingVsTaskDao {
	
	List<MeetingVsTask> getAllMVT();
	MeetingVsTask getMVTById(int taskid);
	void addMeetingVsTask(MeetingVsTask MVT);
	void updateMeetingVsTask(MeetingVsTask MVT,int taskid);

}
