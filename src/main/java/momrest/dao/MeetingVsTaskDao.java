package momrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import momrest.model.MeetingVsTask;

@Transactional
@Repository
public class MeetingVsTaskDao implements IMeetingVsTaskDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<MeetingVsTask> getAllMVT() {
		String sql="FROM MeetingVsTask as mvt ORDER BY mvt.taskid";
		List<MeetingVsTask> MVTList=entityManager.createQuery(sql).getResultList();
		return MVTList;
	}

	@Override
	public MeetingVsTask getMVTById(int taskid) {
		
		return entityManager.find(MeetingVsTask.class, taskid);
	}

	@Override
	public void addMeetingVsTask(MeetingVsTask MVT) {
		entityManager.persist(MVT);

	}

	@Override
	public void updateMeetingVsTask(MeetingVsTask MVT,int taskid) {
		MeetingVsTask meetingvstaskObj=getMVTById(taskid);
		meetingvstaskObj.setResponsible(MVT.getResponsible());
		meetingvstaskObj.setAssignee(MVT.getAssignee());
		meetingvstaskObj.setDuedate(MVT.getDuedate());
		meetingvstaskObj.setSubject(MVT.getSubject());
		meetingvstaskObj.setDescription(MVT.getDescription());
		meetingvstaskObj.setStatus(MVT.getStatus());
		meetingvstaskObj.setUpdatedby(MVT.getUpdatedby());
		entityManager.flush();
	}

}
