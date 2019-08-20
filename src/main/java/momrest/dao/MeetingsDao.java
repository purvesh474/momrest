package momrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import momrest.model.*;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class MeetingsDao implements IMeetings {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<Meetings> getAllUserMeeting(String owner) {
		List<Meetings> meeting=null;
		String hql="From Meetings as sm where sm.owner=?1 or sm.participants LIKE ?2 ORDER BY sm.createddate DESC";
		try {
		meeting=entityManager.createQuery(hql).setParameter(1, owner).setParameter(2, "%"+owner+"%").getResultList();
		
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return meeting;
	}

	@Override
	public Meetings getMeetingById(int id) {
		
		return entityManager.find(Meetings.class, id);
	}

	@Override
	public void addMeeting(Meetings meeting) {
		entityManager.persist(meeting);

	}

	@Override
	public void updateMeeting(Meetings meeting , int id) {
		Meetings meetingByID= getMeetingById(id);
		meetingByID.setSubject(meeting.getSubject());
		//meetingByID.setParticipants(meeting.getParticipants());
		meetingByID.setPlace(meeting.getPlace());
		meetingByID.setNote(meeting.getNote());
		meetingByID.setFile(meeting.getFile());
		meetingByID.setCategory(meeting.getCategory());
		meetingByID.setRecurring(meeting.getRecurring());
		meetingByID.setRecurringapproch(meeting.getRecurringapproch());
		meetingByID.setReferancemeeting(meeting.getReferancemeeting());
		meetingByID.setCustom1(meeting.getCustom1());
		meetingByID.setCustom2(meeting.getCustom2());
		meetingByID.setCustom3(meeting.getCustom3());
		meetingByID.setStartdate(meeting.getStartdate());
		meetingByID.setEnddate(meeting.getEnddate());
		meetingByID.setUpdatedby(meeting.getUpdatedby());
		meetingByID.setStatus(meeting.getStatus());
		meetingByID.setIstaskcreated(meeting.getIstaskcreated());
		entityManager.flush();

	}

	@Override
	public List<Meetings> getAllMeeting() {
		List<Meetings> meeting=null;
		String hql="From Meetings as sm  ORDER BY sm.createddate DESC";
		try {
		meeting=entityManager.createQuery(hql).getResultList();
		
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return meeting;
	}

	public int getLatestMeetingID() {
		int meetingid=0;
		String hql="FROM Meetings  as m where m.meetingid=(select max(mm.meetingid) from Meetings as mm)";
		List<Meetings> listObj=entityManager.createQuery(hql).getResultList();
		if(listObj.size()>0) {
			meetingid=listObj.get(0).getMeetingid();
		}
		return meetingid;
		
	}



	
	

}
