package momrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import momrest.model.CompletedMeeting;

@Transactional
@Repository
public class CompletedMeetingDao implements ICompletedMeeting {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<CompletedMeeting> getAllCompletedMeeting() {
		String hql ="FROM  CompletedMeeting as cm ORDER BY cm.complateddate";
		List<CompletedMeeting> CMeeting=entityManager.createQuery(hql).getResultList();
		return CMeeting;
	}

	@Override
	public CompletedMeeting getCompletedMeetingById(int id) {
		CompletedMeeting CMObj=entityManager.find(CompletedMeeting.class, id);
		return CMObj;
	}

	@Override
	public void addCompletedMeting(CompletedMeeting cmeeting) {
		entityManager.persist(cmeeting);

	}

	@Override
	public void updateCompletedMeeting(CompletedMeeting cmeeting,int id) {
		CompletedMeeting CMObj=getCompletedMeetingById(id);
		CMObj.setSubject(cmeeting.getSubject());
		CMObj.setParticipants(cmeeting.getParticipants());
		CMObj.setPlace(cmeeting.getPlace());
		CMObj.setNote(cmeeting.getNote());
		CMObj.setFile(cmeeting.getFile());
		CMObj.setCatagory(cmeeting.getCatagory());
		CMObj.setReccuring(cmeeting.getReccuring());
		CMObj.setRecurringapproch(cmeeting.getRecurringapproch());
		CMObj.setReferancemeeting(cmeeting.getReferancemeeting());
		CMObj.setCustom1(cmeeting.getCustom1());
		CMObj.setCustom2(cmeeting.getCustom2());
		CMObj.setCustom3(cmeeting.getCustom3());
		CMObj.setComplateddate(cmeeting.getComplateddate());
		CMObj.setIstaskcreated(cmeeting.getIstaskcreated());
		entityManager.flush();

	}

}
