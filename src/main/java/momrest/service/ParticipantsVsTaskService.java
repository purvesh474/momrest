package momrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import momrest.dao.IParticipantsMeeting;
import momrest.dao.IParticipantsVsTaskDao;
import momrest.model.ParticipantsVsTask;

@Service
public class ParticipantsVsTaskService implements IParticipantsVsTaskService {

	@Autowired
	IParticipantsVsTaskDao pvtDao;
	
	@Override
	public List<ParticipantsVsTask> getAllTaskOfParticipants(String userid) {
		
		return pvtDao.getAllTaskOfParticipants(userid);
	}

	@Override
	public synchronized boolean addPVT(ParticipantsVsTask pvt) {
		pvtDao.addPVT(pvt);
		return true;

	}

	@Override
	public void updatePVT(ParticipantsVsTask pvt) {
		pvtDao.updatePVT(pvt);

	}

	public int getTaskCount(String userid) {
		return pvtDao.getTaskCount(userid);
	}
}
