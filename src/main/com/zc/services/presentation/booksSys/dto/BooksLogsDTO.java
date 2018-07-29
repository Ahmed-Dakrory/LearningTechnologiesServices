/**
 * 
 */
package main.com.zc.services.presentation.booksSys.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author omnya
 *
 */
public class BooksLogsDTO {


	private Integer id;
	

	private Calendar date;
	
	private String friendlyDate;

	private BookDTO book;

	private String user;
	

	private String action;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Calendar getDate() {
		return date;
	}


	public void setDate(Calendar date) {
		this.date = date;
	}


	public BookDTO getBook() {
		return book;
	}


	public void setBook(BookDTO book) {
		this.book = book;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
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
	
	
	
	
}
