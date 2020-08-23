/**
 * 
 */
package main.com.zc.services.presentation.booksSys.dto;

import java.util.Calendar;

import main.com.zc.services.domain.shared.enumurations.BookStatusEnum;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
public class BookCopiesDTO {


	private Integer id;
	

	private String barCode;

	private BookDTO book;

	private Calendar addedDate;

	private Calendar lastOper;

	private BookStatusEnum status;
	
	private PersonDataDTO lastPerson;
	

	private String condition;
	private String price;

	

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public BookDTO getBook() {
		return book;
	}

	public void setBook(BookDTO book) {
		this.book = book;
	}

	public Calendar getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Calendar addedDate) {
		this.addedDate = addedDate;
	}

	public Calendar getLastOper() {
		return lastOper;
	}

	public void setLastOper(Calendar lastOper) {
		this.lastOper = lastOper;
	}

	public BookStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BookStatusEnum status) {
		this.status = status;
	}

	public PersonDataDTO getLastPerson() {
		return lastPerson;
	}

	public void setLastPerson(PersonDataDTO lastPerson) {
		this.lastPerson = lastPerson;
	}
	
	
	
	public String getCondition() {
		return condition;
	}

	

	



	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
}
