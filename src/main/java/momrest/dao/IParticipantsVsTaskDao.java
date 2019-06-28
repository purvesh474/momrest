package momrest.dao;

import java.util.List;

import momrest.model.ParticipantsVsTask;

public interface IParticipantsVsTaskDao {

	List<ParticipantsVsTask> getAllTaskOfParticipants(String userid);
	void addPVT(ParticipantsVsTask pvt);
	void updatePVT(ParticipantsVsTask pvt);
}	
