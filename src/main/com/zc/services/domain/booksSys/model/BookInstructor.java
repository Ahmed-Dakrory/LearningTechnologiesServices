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

import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.shared.enumurations.BookActionEnum;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "BookInstructor.getAll", query = "SELECT d FROM BookInstructor d "),
	@NamedQuery(name = "BookInstructor.getById", query = "from BookInstructor d where d.id = :id "),
	@NamedQuery(name = "BookInstructor.getByInsID", query = "from BookInstructor d where d.instructor.id = :id ORDER BY d.date DESC"),
	@NamedQuery(name = "BookInstructor.getByBarCode", query = "from BookInstructor d where d.barCode like :code ORDER BY d.date DESC"),
	@NamedQuery(name = "BookInstructor.getByBarCodeAndInsID", query = "from BookInstructor d where d.barCode like :code and d.instructor.id = :id ORDER BY d.date DESC"),

		})
@Entity
@Table(name = "book_instructor")
public class BookInstructor {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "INSTRUCTOR_ID")
	private Employee instructor;
	
	//@ManyToOne
	@Column(name = "BAR_CODE")
	private String barCode;
	
	@Column(name="DATE")
	private Calendar date;

	@Column(name="ACTION")
	private BookActionEnum action;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getInstructor() {
		return instructor;
	}

	public void setInstructor(Employee instructor) {
		this.instructor = instructor;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public BookActionEnum getAction() {
		return action;
	}

	public void setAction(BookActionEnum action) {
		this.action = action;
	}

	
	
}
