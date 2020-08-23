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
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "Book.getAll", query = "SELECT d FROM Book d "),
	@NamedQuery(name = "Book.getById", query = "from Book d where d.id = :id "),
	@NamedQuery(name = "Book.getByCourseID", query = "from Book d where d.course.id = :id"),
	@NamedQuery(name = "Book.getPendingBook", query = "from Book d where d.status = 0"),
	@NamedQuery(name = "Book.getConfirmedBook", query = "from Book d where d.status = 1"),
		})
@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="NAME")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Courses course;
	

	@ManyToOne
	@JoinColumn(name = "requester_ID")
	private Employee requester;
	

	@ManyToOne
	@JoinColumn(name = "programDirector")
	private Employee programDirector;
	

	@Column(name="pricePerCopy")
	private String pricePerCopy;
	

	@Column(name="purchaseRequest")
	private String purchaseRequest;
	
	@Column(name="ORIGINAL_COPIES")
	private Integer originalCopies;
	
	@Column(name="REMAING_COPIES")
	private Integer remaingCopies;
	
	
	@Column(name = "FROM_DATE")
	private Date fromDate;

	@Column(name = "LAST_EDIT")
	private Date lastDate;

	
	@Column(name="STATUS") //0 pending , 1 confirmed
	private Integer status;

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


	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
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

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Employee getRequester() {
		return requester;
	}

	public void setRequester(Employee requester) {
		this.requester = requester;
	}

	public Employee getProgramDirector() {
		return programDirector;
	}

	public void setProgramDirector(Employee programDirector) {
		this.programDirector = programDirector;
	}

	public String getPricePerCopy() {
		return pricePerCopy;
	}

	public void setPricePerCopy(String pricePerCopy) {
		this.pricePerCopy = pricePerCopy;
	}

	public String getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(String purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}
	
	
	
}
