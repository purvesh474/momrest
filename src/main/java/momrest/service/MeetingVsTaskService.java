package momrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import momrest.dao.IMeetingVsTaskDao;
import momrest.model.MeetingVsTask;

@Service
public class MeetingVsTaskService implements IMeetingVsTaskService {

	@Autowired
	IMeetingVsTaskDao meetingvstaskDao;
	@Override
	public List<MeetingVsTask> getAllList() {
		List<MeetingVsTask> listMeetingVsTask=meetingvstaskDao.getAllMVT();
		return listMeetingVsTask;
	}

	@Override
	public MeetingVsTask getMeetingVsTaskById(int taskid) {
		MeetingVsTask mvt=meetingvstaskDao.getMVTById(taskid);
		return mvt;
	}

	@Override
	public synchronized boolean addMeetingVsTask(MeetingVsTask mvt) {
		meetingvstaskDao.addMeetingVsTask(mvt);
		return true;
	}

	@Override
	public void updateMeetingBVsTask(MeetingVsTask mvt, int taskid) {
		meetingvstaskDao.updateMeetingVsTask(mvt, taskid);

	}

	@Override
	public int getLatesteInsertedValue() {
		
		return meetingvstaskDao.getLatesteInsertedValue();
	}

}
