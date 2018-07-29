/**
 * 
 */
package main.com.zc.services.domain.lectureObjectiveFeedback.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "SemesterWeeks.getAll", query = "SELECT w FROM SemesterWeeks w "),
	@NamedQuery(name = "SemesterWeeks.getById", query = "from SemesterWeeks w where w.id = :id "),
	@NamedQuery(name = "SemesterWeeks.getBySemesterAndyear", query = "from SemesterWeeks w where w.semester= :sem"
			+ " AND w.year= :year"),
	@NamedQuery(name = "SemesterWeeks.getActiveBySemesterAndyear", query = "from SemesterWeeks w where w.semester= :sem"
			+ " AND w.year= :year and w.active=1"),
	
		})

@Entity
@Table(name = "semester_weeks")
public class SemesterWeeks {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="NAME")
	private String name;
	
	
	
	@Column(name="START_DATE")
	private Calendar startDate;
	
	@Column(name="END_DATE")
	private Calendar endDate;

	@Column(name="SEMESTER")
	private Integer semester;
	
	@Column(name="YEAR")
	private Integer year;
	
	@Column(name="ACTIVE")
	private Boolean active;
	
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

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
