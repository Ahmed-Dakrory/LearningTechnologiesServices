/**
 * 
 */
package main.com.zc.services.presentation.attendance.LectureAttenance.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author omnya
 *
 */
public class LectureAttendanceDTO {

	private Integer fileNo;
	private String studentName;
	private Calendar first_in;
	private Calendar last_out;
	private String studentStatus;
	private Calendar date;
	private String firstString;
	private String lastString;
	public Integer getFileNo() {
		return fileNo;
	}
	public void setFileNo(Integer fileNo) {
		this.fileNo = fileNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Calendar getFirst_in() {
		return first_in;
	}
	public void setFirst_in(Calendar first_in) {
		this.first_in = first_in;
	}
	public Calendar getLast_out() {
		return last_out;
	}
	public void setLast_out(Calendar last_out) {
		this.last_out = last_out;
	}
	public String getStudentStatus() {
		return studentStatus;
	}
	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getFirstString() {
		String firendlyDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

		if (getFirst_in() != null) {
			firendlyDate = sdf.format(getFirst_in().getTime());
			return firendlyDate;
		} 
		else return "-";
		
	}
	public void setFirstString(String firstString) {
		this.firstString = firstString;
	}
	public String getLastString() {
		String firendlyDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");

		if (getLast_out() != null) {
			firendlyDate = sdf.format(getLast_out().getTime());
			return firendlyDate;
		} 
		else return "-";
		
	}
	public void setLastString(String lastString) {
		this.lastString = lastString;
	}
	
	
}
