package momrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import momrest.dao.IMeetings;
import momrest.model.Meetings;

@Service
public class Meetingservice implements IMeetingservice {
	
	@Autowired
	IMeetings meetingDao;

	@Override
	public List<Meetings> getAllUserMeeting(String owner) {
		
		return meetingDao.getAllUserMeeting(owner);
	}

	@Override
	public Meetings getMeetingById(int id) {
		// TODO Auto-generated method stub
		return meetingDao.getMeetingById(id) ;
	}

	@Override
	public synchronized boolean addMeeting(Meetings meeting) {
		meetingDao.addMeeting(meeting);
		return true;
	}

	@Override
	public boolean updateMeeting(Meetings meeting, int id) {
		meetingDao.updateMeeting(meeting, id);
		return true;

	}

	@Override
	public List<Meetings> getAllMeeting() {
		// TODO Auto-generated method stub
		return meetingDao.getAllMeeting();
	}

	@Override
	public int getLatestMeetingID() {
		
		return meetingDao.getLatestMeetingID();
	}

	@Override
	public int getMeetingCountV1(String userid, String date) {
		return meetingDao.getMeetingCountV1(userid, date);
	}

	@Override
	public List<Meetings> getMeetingListsV1(String userid, String date) {
		return meetingDao.getMeetingListsV1(userid, date);
	}

	

}
