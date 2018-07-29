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

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.enumurations.BookActionEnum;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "BookStudent.getAll", query = "SELECT d FROM BookStudent d "),
	@NamedQuery(name = "BookStudent.getById", query = "from BookStudent d where d.id = :id "),
	@NamedQuery(name = "BookStudent.getByStudentID", query = "from BookStudent d where d.student.id = :id ORDER BY d.date DESC"),
	@NamedQuery(name = "BookStudent.getByBarCode", query = "from BookStudent d where d.barCode like :code ORDER BY d.date DESC"),
	@NamedQuery(name = "BookStudent.getByBarCodeAndStudentID", query = "from BookStudent d where d.barCode like :code and d.student.id = :id ORDER BY d.date DESC"),

		})
@Entity
@Table(name = "book_student")
public class BookStudent {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
	
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}


	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public BookActionEnum getAction() {
		return action;
	}

	public void setAction(BookActionEnum action) {
		this.action = action;
	}

	
	
}
