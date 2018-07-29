/**
 * 
 */
package main.com.zc.services.presentation.attendance.seminarAttendance.dto;

import java.util.Calendar;

/**
 * @author Omnya Alaa
 *
 */
public class SeminarAttendanceDTO {

	private int id;
	private int fileNo;
	private int personID;
	private Calendar date;
	private Calendar time;
	
	
	public SeminarAttendanceDTO() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public Calendar getTime() {
		return time;
	}
	public void setTime(Calendar time) {
		this.time = time;
	}
	
	
}
