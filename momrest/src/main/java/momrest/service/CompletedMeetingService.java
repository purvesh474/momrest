package momrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import momrest.dao.ICompletedMeeting;
import momrest.model.CompletedMeeting;

public class CompletedMeetingService implements ICompletedMeetingService {

	@Autowired
	private ICompletedMeeting cMeeting;
	
	@Override
	public List<CompletedMeeting> getAllCompletedMeeting() {
		
		return cMeeting.getAllCompletedMeeting();
	}

	@Override
	public synchronized boolean addCompletedMeeting(CompletedMeeting cmeeting) {
		cMeeting.addCompletedMeting(cmeeting);
		return true;
	}

	@Override
	public CompletedMeeting getCompletedMetingById(int id) {
		// TODO Auto-generated method stub
		return cMeeting.getCompletedMeetingById(id);
	}

	@Override
	public void updateCompletedMeeting(CompletedMeeting cmeeting,int id) {
		cMeeting.updateCompletedMeeting(cmeeting, id);

	}

}
