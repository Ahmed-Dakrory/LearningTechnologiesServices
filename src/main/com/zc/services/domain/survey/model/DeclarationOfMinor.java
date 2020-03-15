/**
 * 
 */
package main.com.zc.services.domain.survey.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "DeclarationOfMinor.getAll", query = "SELECT d FROM DeclarationOfMinor d ORDER BY d.id DESC"),
	@NamedQuery(name = "DeclarationOfMinor.getById", query = "from DeclarationOfMinor d where d.id = :id "),
	@NamedQuery(name = "DeclarationOfMinor.getByStudentID", query = "from DeclarationOfMinor d where d.student.id = :id"),
	@NamedQuery(name = "DeclarationOfMinor.getByMinorID", query = "from DeclarationOfMinor d where d.minor = :id"),
	@NamedQuery(name = "DeclarationOfMinor.getAllByYearAndSemester", query = "from DeclarationOfMinor d where d.year = :year AND  d.semester = :semester"),
	@NamedQuery(name = "DeclarationOfMinor.getAllByMinorIDAndYearAndSemester", query = "from DeclarationOfMinor d where d.minor = :id And d.year = :year AND  d.semester = :semester"),
	@NamedQuery(name = "DeclarationOfMinor.getByStudentIDAndYearAndSemester", query = "from DeclarationOfMinor d where d.year = :year AND  d.semester = :semester AND d.student.id = :id")
	
	
	
	})
@Entity
@Table(name = "declaration_of_minor")
public class DeclarationOfMinor {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	@Column(name="MOBILE")
	private String mobile;
	
	
	@ManyToOne
	@JoinColumn(name = "MINOR")
	private Concentration minor;

	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;

	@Column(name="YEAR")
	private Integer year;
	
	@Column(name="SEMESTER")
	private SemesterEnum semester;
	
	@Column(name="state")
	private Integer state;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public SemesterEnum getSemester() {
		return semester;
	}

	public void setSemester(SemesterEnum semester) {
		this.semester = semester;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Concentration getMinor() {
		return minor;
	}

	public void setMinor(Concentration minor) {
		this.minor = minor;
	}

	
	
	
	
}
