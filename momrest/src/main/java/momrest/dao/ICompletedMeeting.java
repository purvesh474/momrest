package momrest.dao;

import java.util.List;

import momrest.model.CompletedMeeting;

public interface ICompletedMeeting {

	List<CompletedMeeting> getAllCompletedMeeting();
	CompletedMeeting getCompletedMeetingById(int id);
	void addCompletedMeting(CompletedMeeting cmeeting);
	void updateCompletedMeeting(CompletedMeeting cmeeting,int id);
}
