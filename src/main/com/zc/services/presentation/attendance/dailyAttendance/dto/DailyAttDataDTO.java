/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.FriendlyDate;

/**
 * @author omnya
 *
 */
public class DailyAttDataDTO {

	private Calendar date;
	private Calendar expected_in;
	private Calendar expected_out;
	private Calendar first_in;
	private Calendar last_out;
	private int personId;
	private String studentName;
	private String studentStatus;
	private String dateString;
	private String friendlyFirstIn;
	private String friendlyLastOut;
	private String sentEmailStatus;
	private String excuse;
	private String excuseStatus;
	private boolean renderSendPet;
	public DailyAttDataDTO(){}

	public DailyAttDataDTO(Calendar date, Calendar expected_in, Calendar expected_out,
			Calendar first_in, Calendar last_out, int personId) {
		super();
		this.date = date;
		this.expected_in = expected_in;
		this.expected_out = expected_out;
		this.first_in = first_in;
		this.last_out = last_out;
		this.personId = personId;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public Calendar getExpected_in() {
		return expected_in;
	}
	public void setExpected_in(Calendar expected_in) {
		this.expected_in = expected_in;
	}
	public Calendar getExpected_out() {
		return expected_out;
	}
	public void setExpected_out(Calendar expected_out) {
		this.expected_out = expected_out;
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
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getDateString() {
		try{
			
		return 	FriendlyDate.getFriendlyDate(getDate());
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getFriendlyFirstIn() {
		String firendlyDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		if (getFirst_in() != null) {
			firendlyDate = sdf.format(getFirst_in().getTime());
		} 
		return firendlyDate;
	}

	public void setFriendlyFirstIn(String friendlyFirstIn) {
		this.friendlyFirstIn = friendlyFirstIn;
	}

	public String getFriendlyLastOut() {
		String firendlyDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		if (getLast_out() != null) {
			firendlyDate = sdf.format(getLast_out().getTime());
		} 
		return firendlyDate;
	}

	public void setFriendlyLastOut(String friendlyLastOut) {
		this.friendlyLastOut = friendlyLastOut;
	}

	public String getSentEmailStatus() {
		return sentEmailStatus;
	}

	public void setSentEmailStatus(String sentEmailStatus) {
		this.sentEmailStatus = sentEmailStatus;
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

	public boolean isRenderSendPet() {
		if(getStudentStatus().trim().equals(Constants.ATTENDANCE_STATUS_SCANNED_ONCE)||
				getStudentStatus().equals(Constants.ATTENDANCE_STATUS_ABSENCE))
			return true;
		return false;
	}

	public void setRenderSendPet(boolean renderSendPet) {
		this.renderSendPet = renderSendPet;
	}
	
	
	
}
