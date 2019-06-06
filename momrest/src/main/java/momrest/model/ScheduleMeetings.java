package momrest.model;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="tblscheduledmeeting")
public class ScheduleMeetings {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int meetingid;
	
	private String subject;
	
	private String participants;
	
	private String owner;
	
	private String place;
	
	private String note;
	private String file;
	
	//Need to change name in db
	private String catagory;
	
	private int recurring;
	
	private int recurringapproch;
	private int referancemeeting;
	
	private String custom1;
	private String custom2;
	private String custom3;
	
	private Timestamp duedate;
	
	
	private Timestamp createddate;
	private Timestamp updateddate;
	private String updatedby;
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
	public void setCatagory(String category) {
		this.catagory = category;
	}
	public int getRecurring() {
		return recurring;
	}
	public void setRecurring(int recurring) {
		this.recurring = recurring;
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
	public Timestamp getDuedate() {
		return duedate;
	}
	public void setDuedate(Timestamp duedate) {
		this.duedate = duedate;
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
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}
