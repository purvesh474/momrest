package momrest.service;

import java.util.List;

import momrest.model.CompletedMeeting;

public interface ICompletedMeetingService {

	List<CompletedMeeting> getAllCompletedMeeting();
	boolean addCompletedMeeting(CompletedMeeting cmeeting);
	CompletedMeeting getCompletedMetingById(int id);
	void updateCompletedMeeting(CompletedMeeting cmeeting,int id);
}
