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
 * @author Omnya
 *
 */
@NamedQueries({
	@NamedQuery(
	name = "Time.getTimeByID",
	query = "from Time t where t.id = :id"
	),
	@NamedQuery(
	name = "Time.getTimeByPersonId",
	query = "from Time t where t.person.id = :personID"
	),
	 @NamedQuery(name="Time.getAll",
     query="SELECT t FROM Time t")
	,
	 @NamedQuery(name="Time.getAllByAttendanceStatus",
    query="SELECT t from Time t where t.attendanceStatus = :attendanceStatus")
	,
	 @NamedQuery(name="Time.getAllByExcuseStatus",
   query="SELECT t from Time t where t.excuseStatus = :excuseStatus")
	, 
	@NamedQuery(name="Time.getAllByAttendanceDate",
	 query="SELECT t from Time t where t.date = :date")
	,
	 @NamedQuery(name="Time.getAllByAttendanceStatusAndDate",
   query="SELECT t from Time t where t.attendanceStatus = :attendanceStatus and t.date = :date")
	,
	 @NamedQuery(name="Time.getAllByAttendanceFileNoAndDate",
	   query="SELECT t from Time t where t.person.fileNo = :fileNo and t.date = :date")
		,
	 @NamedQuery(name="Time.getTimeByFileNoAndDate",
  query="SELECT t from Time t where t.person.fileNo = :fileNo and t.date = :date")
	,
	 @NamedQuery(name="Time.getAllAttendanceByFileNo",
 query="SELECT t from Time t where t.person.fileNo = :fileNo"),
 @NamedQuery(name="Time.getAllAttendanceByFileNoAndStatus",
 query="SELECT t from Time t where t.person.fileNo = :fileNo and t.attendanceStatus = :status")
})
@Entity
@Table(name = "time")
public class Time {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	
	@Column(name="DATE")
	Calendar date;
	
	@Column(name="EXPECTED_IN")
	Calendar expectedIn;
	
	
	@Column(name="EXPECTED_OUT")
	Calendar expectedOut;
	

	@Column(name="FIRST_IN")
	Calendar firstIn;
	
	@Column(name="LAST_OUT")
	Calendar firstOut;
	
	@Column(name="ATTENDANCE_STATUS")
	String attendanceStatus;
	
	@Column(name="SEND_MAIL_STATUS")
	String sendMailStatus;
	

	@Column(name="EXCUSE")
	String excuse;
	
	
	@Column(name="EXCUSE_STATUS")
	String excuseStatus;
	

	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	private Student person;

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

	
	public Time(){}
	public Time(Calendar date, Calendar expectedIn, Calendar expectedOut,
			Calendar firstIn, Calendar firstOut, Student person) {
		super();
		this.date = date;
		this.expectedIn = expectedIn;
		this.expectedOut = expectedOut;
		this.firstIn = firstIn;
		this.firstOut = firstOut;
		this.person = person;
	}
	public Time( Calendar date, Calendar expectedIn,
			Calendar expectedOut, Calendar firstIn, Calendar firstOut,
			String attendanceStatus, String sendMailStatus, Student person) {
		super();
		//this.id = id;
		this.date = date;
		this.expectedIn = expectedIn;
		this.expectedOut = expectedOut;
		this.firstIn = firstIn;
		this.firstOut = firstOut;
		this.attendanceStatus = attendanceStatus;
		this.sendMailStatus = sendMailStatus;
		this.person = person;
	}
	public Student getPerson() {
		return person;
	}

	public void setPerson(Student person) {
		this.person = person;
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

	public Calendar getExpectedIn() {
		return expectedIn;
	}

	public void setExpectedIn(Calendar expectedIn) {
		this.expectedIn = expectedIn;
	}

	public Calendar getExpectedOut() {
		return expectedOut;
	}

	public void setExpectedOut(Calendar expectedOut) {
		this.expectedOut = expectedOut;
	}

	public Calendar getFirstIn() {
		return firstIn;
	}

	public void setFirstIn(Calendar firstIn) {
		this.firstIn = firstIn;
	}

	public Calendar getFirstOut() {
		return firstOut;
	}

	public void setFirstOut(Calendar firstOut) {
		this.firstOut = firstOut;
	}
	public String getExcuse() {
		return excuse;
	}
	public void setExcuse(String excuse) {
		this.excuse = excuse;
	}
	public String getExcuseStatus() {
		return excuseStatus;
	}
	public void setExcuseStatus(String excuseStatus) {
		this.excuseStatus = excuseStatus;
	}

}
