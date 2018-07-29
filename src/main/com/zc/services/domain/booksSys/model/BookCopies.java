/**
 * 
 */
package main.com.zc.services.domain.booksSys.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.shared.enumurations.BookStatusEnum;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "BookCopies.getAll", query = "SELECT d FROM BookCopies d "),
	@NamedQuery(name = "BookCopies.getById", query = "from BookCopies d where d.id = :id "),
	@NamedQuery(name = "BookCopies.getByCourseID", query = "from BookCopies d where d.book.course.id = :id"),
	@NamedQuery(name = "BookCopies.getByBookID", query = "from BookCopies d where d.book.id = :id"),
	@NamedQuery(name = "BookCopies.getByBarCode", query = "from BookCopies d where d.barCode like :code"),

		})
@Entity
@Table(name = "book_copies")
public class BookCopies {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="BAR_CODE")
	private String barCode;
	
	@ManyToOne
	@JoinColumn(name = "BOOK_ID")
	private Book book;

	@Column(name = "ADDED_DATE")
	private Date addedDate;

	@Column(name = "LAST_OPERATION_DATE")
	private Date lastOper;
	
	@Column(name = "STATUS")
	private BookStatusEnum status;
	
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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Date getLastOper() {
		return lastOper;
	}

	public void setLastOper(Date lastOper) {
		this.lastOper = lastOper;
	}

	public BookStatusEnum getStatus() {
		return status;
	}

	public void setStatus(BookStatusEnum status) {
		this.status = status;
	}

	
	
}
