/**
 * 
 */
package main.com.zc.services.presentation.booksSys.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class BookDTO {
private Integer id;
	private String name;
	
	private CoursesDTO course;
	
	private Integer originalCopies;

	private Integer remaingCopies;
	

	private Integer reservedCopies;
	
	
	private Calendar fromDate;

	private Calendar lastDate;

    //0 pending , 1 confirmed
	private Integer status;
	
	private List<BooksLogsDTO> logs=new ArrayList<BooksLogsDTO>();


	public Integer getId() {
		return id;
	}





	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReservedCopies(Integer reservedCopies) {
		this.reservedCopies = reservedCopies;
	}

	public Integer getReservedCopies() {
		return (originalCopies-remaingCopies);
	}
	public CoursesDTO getCourse() {
		return course;
	}


	public void setCourse(CoursesDTO course) {
		this.course = course;
	}


	public Integer getOriginalCopies() {
		return originalCopies;
	}





	public void setOriginalCopies(Integer originalCopies) {
		this.originalCopies = originalCopies;
	}





	public Integer getRemaingCopies() {
		return remaingCopies;
	}





	public void setRemaingCopies(Integer remaingCopies) {
		this.remaingCopies = remaingCopies;
	}

	public Integer getStatus() {
		return status;
	}





	public Calendar getFromDate() {
		return fromDate;
	}





	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}





	public Calendar getLastDate() {
		return lastDate;
	}





	public void setLastDate(Calendar lastDate) {
		this.lastDate = lastDate;
	}





	public void setStatus(Integer status) {
		this.status = status;
	}





	public List<BooksLogsDTO> getLogs() {
		return logs;
	}





	public void setLogs(List<BooksLogsDTO> logs) {
		this.logs = logs;
	}

	

	
}
