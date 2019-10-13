package momrest.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
		meetingvstaskObj.setTasktype(MVT.getTasktype());
		meetingvstaskObj.setResponsible(MVT.getResponsible());
		meetingvstaskObj.setAssignee(MVT.getAssignee());
		meetingvstaskObj.setDuedate(MVT.getDuedate());
		meetingvstaskObj.setSubject(MVT.getSubject());
		meetingvstaskObj.setDescription(MVT.getDescription());
		meetingvstaskObj.setStatus(MVT.getStatus());
		meetingvstaskObj.setUpdatedby(MVT.getUpdatedby());
		entityManager.flush();
	}
	
	public int getLatesteInsertedValue() {
		String hql="FROM MeetingVsTask as m where m.taskid=(select max(mm.taskid) from MeetingVsTask as mm)";
		List<MeetingVsTask> listObj=entityManager.createQuery(hql).getResultList();
		return listObj.get(0).getTaskid();
	
	}

	@Override
	public int getTaskCountV1(String userid, String tasktype, String date) {
		
		Query query = null;
		int count=0;
		StringBuilder qry = new StringBuilder("SELECT COUNT(*) FROM tblmeetingvstask WHERE (assignee=:assignee OR responsible like :userid)");
		
		if(tasktype != null && !tasktype.isEmpty()){
			qry.append(" AND tasktype=:tasktype");
		}
		
		if(date != null && !date.isEmpty()){
			qry.append(" AND duedate >= NOW()");
		}
		qry.append(" order by createddate desc");
		
		query = entityManager.createNativeQuery(qry.toString());	
		query.setParameter("assignee", userid);
		query.setParameter("userid", "%"+userid+"%");	
		if(tasktype != null && !tasktype.isEmpty())
			query.setParameter("tasktype", tasktype);
		
		count = ((Number) query.getSingleResult()).intValue();
		return count;
		
	}

	@Override
	public List<MeetingVsTask> getTaskListsV1(String userid, String tasktype, String date) {
		
		StringBuilder qry = new StringBuilder("SELECT * FROM tblmeetingvstask WHERE (assignee=:assignee OR responsible like :userid)");
		List<MeetingVsTask> MVTList = new ArrayList<MeetingVsTask>();
		Query query = null;
		
		if(tasktype != null && !tasktype.isEmpty()){
			qry.append(" AND tasktype=:tasktype");
		}
		
		if(date != null && !date.isEmpty()){
			qry.append(" AND duedate >= NOW()");
		}
		qry.append(" order by createddate desc");
		
		query = entityManager.createNativeQuery(qry.toString());
		query.setParameter("assignee", userid);
		query.setParameter("userid", "%"+userid+"%");	
		if(tasktype != null && !tasktype.isEmpty())
			query.setParameter("tasktype", tasktype);
		
		MVTList = (List<MeetingVsTask>) query.getResultList();
		return MVTList;
	}

	@Override
	public List<MeetingVsTask> getMeetVsTaskByMeetingId(int meetingId) {
		
		List<MeetingVsTask> MVTList = new ArrayList<MeetingVsTask>();
		
		Query query = entityManager.createNativeQuery("SELECT * FROM tblmeetingvstask WHERE meetingid=:meetingid order by createddate desc");
		query.setParameter("meetingid", meetingId);
		MVTList = (List<MeetingVsTask>) query.getResultList();
		return MVTList;
		
	}

}
