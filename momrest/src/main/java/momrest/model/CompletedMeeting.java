package momrest.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
//Need To change table name in db
@Table(name="tblcomplatedmeeting")
public class CompletedMeeting {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int meetingid;
	
	private String subject;
	
	private String participants;
	
	private String owner;
	
	private String place;
	
	private String note;
	
	private String file;
	//Need To change name in db
	private String catagory;
	
	private  int reccuring;
	
	private int recurringapproch;
	
	private int referancemeeting;
	
	private String custom1;
	private String custom2;
	private String custom3;
	
	//Need to change column name in db
	private Timestamp complateddate;
	
	private Timestamp createddate;
	
	private Timestamp updateddate;
	
	//Change dataType in DB
	private int updatedby;
	
	private int istaskcreated;

	
	public int getReccuring() {
		return reccuring;
	}

	public void setReccuring(int reccuring) {
		this.reccuring = reccuring;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMeetingid() {
		return meetingid;
	}

	public void setMeetingid(int meetingid) {
		this.meetingid = meetingid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public int getRecurringapproch() {
		return recurringapproch;
	}

	public void setRecurringapproch(int recurringapproch) {
		this.recurringapproch = recurringapproch;
	}

	public int getReferancemeeting() {
		return referancemeeting;
	}

	public void setReferancemeeting(int referancemeeting) {
		this.referancemeeting = referancemeeting;
	}

	public String getCustom1() {
		return custom1;
	}

	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}

	public String getCustom2() {
		return custom2;
	}

	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}

	public String getCustom3() {
		return custom3;
	}

	public void setCustom3(String custom3) {
		this.custom3 = custom3;
	}

	public Timestamp getComplateddate() {
		return complateddate;
	}

	public void setComplateddate(Timestamp complateddate) {
		this.complateddate = complateddate;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public Timestamp getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}

	public int getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(int updatedby) {
		this.updatedby = updatedby;
	}

	public int getIstaskcreated() {
		return istaskcreated;
	}

	public void setIstaskcreated(int istaskcreated) {
		this.istaskcreated = istaskcreated;
	}
}
