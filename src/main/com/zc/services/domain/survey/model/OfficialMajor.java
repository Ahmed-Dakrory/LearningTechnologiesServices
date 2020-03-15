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

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "OfficialMajor.getAll", query = "SELECT d FROM OfficialMajor d ORDER BY d.id DESC"),
	@NamedQuery(name = "OfficialMajor.getById", query = "from OfficialMajor d where d.id = :id "),
	@NamedQuery(name = "OfficialMajor.getByStudentID", query = "from OfficialMajor d where d.student.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "OfficialMajor.getByMajorID", query = "from OfficialMajor d where d.major.id= :id  ORDER BY d.id DESC"),
	@NamedQuery(name = "OfficialMajor.getByMajorIDAndYearAndSemester", query = "from OfficialMajor d where d.major.id= :id AND d.year= :year AND d.semester= :semester "),
	@NamedQuery(name = "OfficialMajor.getByMajorHead", query = "from OfficialMajor d where d.major.headOfMajorId.id= :id ORDER BY d.id DESC"),
	@NamedQuery(name = "OfficialMajor.getByYearAndSemester", query = "from OfficialMajor d where d.year= :year AND d.semester= :semester"),
	@NamedQuery(name = "OfficialMajor.getByStudentIDAndYearAndSemester", query = "from OfficialMajor d where d.year= :year AND d.semester= :semester AND d.student.id = :id")
	
	})

@Entity
@Table(name = "official_major")
public class OfficialMajor {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	@Column(name="MOBILE")
	private String mobile;
	
	@ManyToOne
	@JoinColumn(name = "MAJOR_ID")
	private Majors major;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;

	@Column(name="YEAR")
	private Integer year;
	
	@Column(name="SEMESTER")
	private SemesterEnum semester;
	
	@Column(name="state")
	private Integer state;
	

	public static int STATE_Waiting = 0;
	public static int STATE_ACCEPTED = 1;
	public static int STATE_Refused = 2;
	
	
	public OfficialMajor() {
		super();
	}

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

	public Majors getMajor() {
		return major;
	}

	public void setMajor(Majors major) {
		this.major = major;
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
	
	
	
}
