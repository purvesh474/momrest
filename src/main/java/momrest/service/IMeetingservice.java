package momrest.service;

import java.util.List;

import momrest.model.Meetings;

public interface IMeetingservice {

	List<Meetings> getAllMeeting();
	List<Meetings> getAllUserMeeting(String owner);
	Meetings getMeetingById(int id);
	boolean addMeeting(Meetings meeting);
	boolean updateMeeting(Meetings meeting,int id);
	public int getLatestMeetingID();
}
