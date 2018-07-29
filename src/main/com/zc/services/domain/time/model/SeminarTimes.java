/**
 * 
 */
package main.com.zc.services.domain.time.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.person.model.Student;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


/**
 * @author Omnya Alaa
 *
 */
@NamedQueries({
	@NamedQuery(
	name = "SeminarTimes.getSeminarTimesByID",
	query = "from SeminarTimes st where st.id = :id"
	),
	@NamedQuery(
	name = "SeminarTimes.getSeminarTimesByPersonId",
	query = "from SeminarTimes st where st.person.id = :personID"
	),
	 @NamedQuery(name="SeminarTimes.getAll",
     query="SELECT st FROM SeminarTimes st")
	,
	 @NamedQuery(name="SeminarTimes.getAllByAttendanceStatus",
    query="SELECT st from SeminarTimes st where st.attendanceStatus = :attendanceStatus")
	,
	 @NamedQuery(name="SeminarTimes.getAllByAttendanceStatusAndDate",
   query="SELECT st from SeminarTimes st where st.attendanceStatus = :attendanceStatus and st.date = :date")
	,
	 @NamedQuery(name="SeminarTimes.getSeminarTimesByFileNoAndDate",
  query="SELECT st from SeminarTimes st where st.person.fileNo = :fileNo and st.date = :date")
	,
	 @NamedQuery(name="SeminarTimes.getAllAttendanceByFileNo",
 query="SELECT st from SeminarTimes st where st.person.fileNo = :fileNo")
})
@Entity
@Table(name = "seminar_time")
public class SeminarTimes {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	@Column(name="DATE")
	Calendar date;
	
	@Column(name="ATTEND_TIME")
	Calendar attendTime;
	
	@Column(name="LEAVE_TIME")
	Calendar leaveTime;
	
	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	private Student person;
	
	@Column(name="ATTENDANCE_STATUS")
	String attendanceStatus;
	
	@Column(name="SENT_MAIL_STATUS")
	String sendMailStatus;

	
	

	public SeminarTimes() {
		super();
	}
	public SeminarTimes(Calendar date, Calendar attendTime, Calendar leaveTime,
			Student person, String attendanceStatus, String sendMailStatus) {
		super();
		this.date = date;
		this.attendTime = attendTime;
		this.leaveTime = leaveTime;
		this.person = person;
		this.attendanceStatus = attendanceStatus;
		this.sendMailStatus = sendMailStatus;
	}
	
	public SeminarTimes( Calendar date, Calendar attendTime,
			Student person, String attendanceStatus) {
		super();
		this.id = id;
		this.date = date;
		this.attendTime = attendTime;
		this.person = person;
		this.attendanceStatus = attendanceStatus;
		this.sendMailStatus = sendMailStatus;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getAttendTime() {
		return attendTime;
	}

	public void setAttendTime(Calendar attendTime) {
		this.attendTime = attendTime;
	}

	public Calendar getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Calendar leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Student getPerson() {
		return person;
	}

	public void setPerson(Student person) {
		this.person = person;
	}

	public String getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public String getSendMailStatus() {
		return sendMailStatus;
	}

	public void setSendMailStatus(String sendMailStatus) {
		this.sendMailStatus = sendMailStatus;
	}
}
