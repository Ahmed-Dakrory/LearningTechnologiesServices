/**
 * 
 */
package main.com.zc.services.presentation.attendance.petitions.dto;

import java.util.Calendar;
import java.util.List;

/**
 * @author Omnya Alaa
 *
 */
public class DailyAttPetDto {

	private int fileNo;
	private String excuse;
	private List<Calendar> dates;
	public DailyAttPetDto() {
		super();
	}
	
	public DailyAttPetDto(int fileNo, String excuse, List<Calendar> dates) {
		super();
		this.fileNo = fileNo;
		this.excuse = excuse;
		this.dates = dates;
	}

	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public String getExcuse() {
		return excuse;
	}
	public void setExcuse(String excuse) {
		this.excuse = excuse;
	}
	public List<Calendar> getDates() {
		return dates;
	}
	public void setDates(List<Calendar> dates) {
		this.dates = dates;
	}
	
	
}
