package momrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import io.micrometer.shaded.org.pcollections.PMap;
import momrest.model.ParticipantsVsMeeting;

@Transactional
@Repository
public class ParticipantsVsMeetingDao implements IParticipantsMeeting {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<ParticipantsVsMeeting> getAllPVMByUserid(int userid) {
		String hql="FROM ParticipantsVsMeeting as p where p.userid=?1";
		List<ParticipantsVsMeeting> pvmList=entityManager.createQuery(hql).setParameter(1, userid).getResultList();
		return pvmList;
	}

	@Override
	public void addPVM(momrest.model.ParticipantsVsMeeting pvm) {
		entityManager.persist(pvm);

	}

	@Override
	public List<momrest.model.ParticipantsVsMeeting> getAllPVMByMeetingId(int meetingid) {
		String hql="FROM ParticipantsVsMeeting as p where p.meetingid=?1";
		List<momrest.model.ParticipantsVsMeeting> pvmList=entityManager.createQuery(hql).setParameter(1, meetingid).getResultList();
		return pvmList;
	}

	@Override
	public void updatePVM(momrest.model.ParticipantsVsMeeting pvm) {
		List<ParticipantsVsMeeting> pvmList=getPVMUidMid(pvm.getUserid(), pvm.getMeetingid());
		if(pvmList.size()==0 || pvmList==null) {
			addPVM(pvm);
		}
	}

	@Override
	public List<ParticipantsVsMeeting> getPVMUidMid(String userid, int meetingid) {
		String hql="FROM ParticipantsVsMeeting as p where p.userid=?1 and p.meetingid=?2";
		List<ParticipantsVsMeeting> pvm=entityManager.createQuery(hql).setParameter(1, userid).setParameter(2, meetingid).getResultList();
		return pvm;
	}

	@Override
	public int getMeetingCount(String userid) {
		String hql="FROM ParticipantsVsMeeting as p where p.userid=?1";
		List<ParticipantsVsMeeting> listObj=entityManager.createQuery(hql).setParameter(1, userid).getResultList();
			return listObj.size();
	}
	
	@Override
	public int deleteParticipants(String participants,int meetingid) {
		String sql="delete from ParticipantsVsMeeting where userid=?1 and meetingid=?2";
		int i=0;
		try {
			
		i=entityManager.createQuery(sql).setParameter(1, participants).setParameter(2, meetingid).executeUpdate();
		System.out.println("Delete "+i);
		}catch (Exception e) {
			System.out.println(e);
		}
	
		return i;
	}

}
