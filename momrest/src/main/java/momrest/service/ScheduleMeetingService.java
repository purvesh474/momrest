package momrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import momrest.dao.IScheduledMeeting;
import momrest.model.ScheduleMeetings;

@Service
public class ScheduleMeetingService implements IScheduleMeetingService {
	
	@Autowired
	IScheduledMeeting meetingDao;

	@Override
	public List<ScheduleMeetings> getAllMeeting() {
		
		return meetingDao.getAllMeeting();
	}

	@Override
	public ScheduleMeetings getMeetingById(int id) {
		// TODO Auto-generated method stub
		return meetingDao.getMeetingById(id) ;
	}

	@Override
	public synchronized boolean addMeeting(ScheduleMeetings meeting) {
		meetingDao.addMeeting(meeting);
		return true;
	}

	@Override
	public boolean updateMeeting(ScheduleMeetings meeting, int id) {
		meetingDao.updateMeeting(meeting, id);
		return true;

	}

}
