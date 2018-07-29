/**Book
 * 
 */
package main.com.zc.services.presentation.booksSys.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import main.com.zc.services.domain.shared.enumurations.BookActionEnum;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class BookStudentDTO {


	private Integer id;
	

	private StudentDTO student;
	

	private BookDTO book;
	private String barCode;

	private Calendar date;

    private String friendlyDate;
	
    private BookActionEnum action;
    
    
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public StudentDTO getStudent() {
		return student;
	}


	public void setStudent(StudentDTO student) {
		this.student = student;
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
	
	
	
}
