/**
 * 
 */
package main.com.zc.services.presentation.attendance.shared.dto;

import java.util.Calendar;

/**
 * @author Omnya Alaa
 *
 */
public class TimeDailyAttDTO {

	private int id;
	private String attStatus;
	private Calendar date;
	private String sendMailStatus;
	public TimeDailyAttDTO() {
		super();
	}
	public TimeDailyAttDTO(int id, String attStatus, Calendar date,
			String sendMailStatus) {
		super();
		this.id = id;
		this.attStatus = attStatus;
		this.date = date;
		this.sendMailStatus = sendMailStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAttStatus() {
		return attStatus;
	}
	public void setAttStatus(String attStatus) {
		this.attStatus = attStatus;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getSendMailStatus() {
		return sendMailStatus;
	}
	public void setSendMailStatus(String sendMailStatus) {
		this.sendMailStatus = sendMailStatus;
	}
	
	
}
