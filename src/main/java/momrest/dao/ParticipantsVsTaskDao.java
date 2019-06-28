package momrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import momrest.model.ParticipantsVsTask;

@Transactional
@Repository
public class ParticipantsVsTaskDao implements IParticipantsVsTaskDao {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<ParticipantsVsTask> getAllTaskOfParticipants(String userid) {
		String hql="FROM ParticipantsVsTask as p where p.userid=?1";
		List<ParticipantsVsTask> pvtList=entityManager.createQuery(hql).setParameter(1, userid).getResultList();
		return pvtList;
	}

	@Override
	public void addPVT(ParticipantsVsTask pvt) {
		entityManager.persist(pvt);
	}

	@Override
	public void updatePVT(ParticipantsVsTask pvt) {
		List<ParticipantsVsTask> pvtlist=checkUIDAndTID(pvt.getUserid(), pvt.getTaskid());
		if(pvtlist==null || pvtlist.size()==0 ) {
			
		}
		
	}
	
	private List<ParticipantsVsTask> checkUIDAndTID(String uid,int taskid) {
		
		String hql="FROM ParticipantsVsTask as p where p.userid=?1 and p.taskid=?2";
		List<ParticipantsVsTask> pvtobj=entityManager.createQuery(hql).setParameter(1, uid).setParameter(2, taskid).getResultList();
		return pvtobj;
		
	}
	
	

}
