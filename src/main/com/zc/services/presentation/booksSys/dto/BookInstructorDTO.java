/**
 * 
 */
package main.com.zc.services.presentation.booksSys.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.com.zc.services.domain.shared.enumurations.BookActionEnum;
import main.com.zc.services.presentation.users.dto.InstructorDTO;


/**
 * @author omnya
 *
 */
public class BookInstructorDTO {


	private Integer id;
	

	private InstructorDTO instructor;
	

	private BookDTO book;
	
	private String barCode;
	private Calendar date;

	private String friendlyDate;
	
	private BookActionEnum action;
	

	private String condition;
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public InstructorDTO getInstructor() {
		return instructor;
	}


	public void setInstructor(InstructorDTO instructor) {
		this.instructor = instructor;
	}


	public BookDTO getBook() {
		return book;
	}


	public void setBook(BookDTO book) {
		this.book = book;
	}


	public Calendar getDate() {
		return date;
	}


	public void setDate(Calendar date) {
		this.date = date;
	}


	public String getFriendlyDate() {
		if(getDate()!=null){
			 
			   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			    String strDate = sdf.format(getDate().getTime());
			    return strDate;
			}
			
			else return "";
	}


	public void setFriendlyDate(String friendlyDate) {
		this.friendlyDate = friendlyDate;
	}


	public BookActionEnum getAction() {
		return action;
	}


	public void setAction(BookActionEnum action) {
		this.action = action;
	}


	public String getBarCode() {
		return barCode;
	}


	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
	
}
