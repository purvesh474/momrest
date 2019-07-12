package momrest.service;

import java.util.List;

import momrest.model.ParticipantsVsTask;

public interface IParticipantsVsTaskService {

	
	List<ParticipantsVsTask> getAllTaskOfParticipants(String userid);
	boolean addPVT(ParticipantsVsTask pvt);
	void updatePVT(ParticipantsVsTask pvt);
	 int getTaskCount(String userid);
	
}
