package momrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import momrest.dao.IMeetingVsTaskDao;
import momrest.dao.IParticipantsMeeting;
import momrest.model.ParticipantsVsMeeting;

@Service
public class ParticipantsVsMeetingService implements IParticipantsVsMeetingService {

	@Autowired
	IParticipantsMeeting pvmdao;
	
	@Override
	public List<ParticipantsVsMeeting> getAllPVMByUserid(int userid) {
		List<ParticipantsVsMeeting> pvmList=pvmdao.getAllPVMByUserid(userid);
		return pvmList;
	}

	@Override
	public synchronized boolean addPVM(ParticipantsVsMeeting pvm) {
		pvmdao.addPVM(pvm);
		return true;

	}

	@Override
	public List<ParticipantsVsMeeting> getAllPVMByMeetingId(int meetingid) {
		List<ParticipantsVsMeeting> pvmList=pvmdao.getAllPVMByMeetingId(meetingid);
		return pvmList;
	}

	@Override
	public void updatePVM(ParticipantsVsMeeting pvm) {
		pvmdao.updatePVM(pvm);
	}

	@Override
	public List<ParticipantsVsMeeting> getPVMUidMid(int userid, int meetingid) {
		// TODO Auto-generated method stub
		return null;
	}

}
