package momrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import momrest.model.*;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class ScheduleMeetingsDao implements IScheduledMeeting {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<ScheduleMeetings> getAllMeeting() {
		
		String hql="From ScheduledMeeting as sm ORDER BY sm.createddate";
		List<ScheduleMeetings> meeting=entityManager.createQuery(hql).getResultList();
		return meeting;
	}

	@Override
	public ScheduleMeetings getMeetingById(int id) {
		
		return entityManager.find(ScheduleMeetings.class, id);
	}

	@Override
	public void addMeeting(ScheduleMeetings meeting) {
		entityManager.persist(meeting);

	}

	@Override
	public void updateMeeting(ScheduleMeetings meeting , int id) {
		ScheduleMeetings meetingByID= getMeetingById(id);
		meetingByID.setSubject(meeting.getSubject());
		meetingByID.setParticipants(meeting.getParticipants());
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
		meetingByID.setDuedate(meeting.getDuedate());
		meetingByID.setUpdatedby(meeting.getUpdatedby());
		entityManager.flush();

	}

	

}
