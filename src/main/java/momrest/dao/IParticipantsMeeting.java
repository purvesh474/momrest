package momrest.dao;

import java.util.List;

import momrest.model.ParticipantsVsMeeting;

public interface IParticipantsMeeting {

	List<ParticipantsVsMeeting> getAllPVMByUserid(int userid);
	void addPVM(ParticipantsVsMeeting pvm);
	List<ParticipantsVsMeeting> getAllPVMByMeetingId(int meetingid);
	void updatePVM(ParticipantsVsMeeting pvm);
	List<ParticipantsVsMeeting> getPVMUidMid(String userid,int meetingid); 
	int getMeetingCount(String userid);
	
}
