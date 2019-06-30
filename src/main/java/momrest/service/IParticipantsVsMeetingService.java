package momrest.service;

import java.util.List;

import momrest.model.ParticipantsVsMeeting;

public interface IParticipantsVsMeetingService {

	List<ParticipantsVsMeeting> getAllPVMByUserid(int userid);
	boolean addPVM(ParticipantsVsMeeting pvm);
	List<ParticipantsVsMeeting> getAllPVMByMeetingId(int meetingid);
	void updatePVM(ParticipantsVsMeeting pvm);
	List<ParticipantsVsMeeting> getPVMUidMid(int userid,int meetingid);
	public int getMeetingCount(String userid);
	
}
