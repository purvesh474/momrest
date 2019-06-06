package momrest.service;

import java.util.List;

import momrest.model.ScheduleMeetings;

public interface IScheduleMeetingService {

	List<ScheduleMeetings> getAllMeeting();
	ScheduleMeetings getMeetingById(int id);
	boolean addMeeting(ScheduleMeetings meeting);
	boolean updateMeeting(ScheduleMeetings meeting,int id);
	
}
