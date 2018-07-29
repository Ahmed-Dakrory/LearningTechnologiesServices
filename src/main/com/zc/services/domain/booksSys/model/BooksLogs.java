/**
 * 
 */
package main.com.zc.services.domain.booksSys.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "BooksLogs.getAll", query = "SELECT d FROM BooksLogs d "),
	@NamedQuery(name = "BooksLogs.getById", query = "from BooksLogs d where d.id = :id "),
	@NamedQuery(name = "BooksLogs.getByUserName", query = "from BooksLogs d where d.user = :user"),
	@NamedQuery(name = "BooksLogs.getByBookID", query = "from BooksLogs d where d.book.id = :id")

		})
@Entity
@Table(name = "books_log")
public class BooksLogs {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="DATE")
	private Calendar date;
	
	
	@ManyToOne
	@JoinColumn(name = "BOOK_ID")
	private Book book;
	
	@Column(name="USER")
	private String user;
	
	@Column(name="ACTION")
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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
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
	
	
	
}
