package momrest.dao;

import java.util.List;

import momrest.model.Meetings;

public interface IMeetings{

	List<Meetings> getAllUserMeeting(String owner);
	Meetings getMeetingById(int id);
	void addMeeting(Meetings meeting);
	void updateMeeting(Meetings meeting,int id);
	List<Meetings> getAllMeeting();
	int getLatestMeetingID();
	
	
	
}
