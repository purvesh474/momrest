package momrest.dao;

import java.util.List;

import momrest.model.ScheduleMeetings;

public interface IScheduledMeeting {

	List<ScheduleMeetings> getAllMeeting();
	ScheduleMeetings getMeetingById(int id);
	void addMeeting(ScheduleMeetings meeting);
	void updateMeeting(ScheduleMeetings meeting,int id);
	
	
	
}
