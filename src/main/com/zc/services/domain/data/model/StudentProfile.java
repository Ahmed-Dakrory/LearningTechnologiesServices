/**
 * 
 */
package main.com.zc.services.domain.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.domain.survey.model.Concentration;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({
	@NamedQuery(
	name = "StudentProfile.getByStudentID",
	query = "from StudentProfile p where p.student.id = :id"
	),
	@NamedQuery(
	name = "StudentProfile.getById",
	query = "from StudentProfile p where p.id = :id"
	),
	
	@NamedQuery(
	name = "StudentProfile.getStudentMail",
	query = "from StudentProfile p where LOWER(p.student.data.mail) LIKE :mail"
	),
	 @NamedQuery(name="StudentProfile.getAll",
     query="SELECT p FROM StudentProfile p"
     )
	,

	 @NamedQuery(name="StudentProfile.getBySemesterAndYear",
   query="from StudentProfile s where s.semester =  :sem AND s.year = :year"
   ),
   @NamedQuery(name="StudentProfile.getBySemesterAndYearAndStudentId",
   query="from StudentProfile s where s.semester =  :sem AND s.year = :year AND s.student.id = :id"
   )
})

@Entity
@Table(name = "student_profile")
@Inheritance(strategy= InheritanceType.JOINED)
public class StudentProfile {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer  id;

	
	@ManyToOne
	@JoinColumn(name = "CURRENT_MAJOR")
	private Majors major;
	
	@Column(name="CURRENT_CREDIT_HRS")
	private Double currentCreditHrs;

	@Column(name="COMPLETED_CREDIT_HRS")
	private Double completedCreditHrs;
	

	@Column(name="attempt_credit_hours")
	private Double attempt_credit_hours;
	
	@Column(name="REPEATED_COURSES")
	private Integer repeatedCourses;
	
	
	@Column(name = "SEMESTER")
	private SemesterEnum semester;
	
	
	
	@Column(name = "YEAR")
	private Integer  year;

	@Column(name="GPA")
	private Double gpa;

	@ManyToOne
	@JoinColumn(name = "STUDENT")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "concentration")
	private Concentration concentration;
	

	@Column(name="minor")
	private String minor;
	
	@Column(name="transcript")
	private String transcript;
	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Majors getMajor() {
		return major;
	}



	public void setMajor(Majors major) {
		this.major = major;
	}



	public Double getCurrentCreditHrs() {
		return currentCreditHrs;
	}



	public void setCurrentCreditHrs(Double currentCreditHrs) {
		this.currentCreditHrs = currentCreditHrs;
	}



	public Double getCompletedCreditHrs() {
		return completedCreditHrs;
	}



	public void setCompletedCreditHrs(Double completedCreditHrs) {
		this.completedCreditHrs = completedCreditHrs;
	}



	public Integer getRepeatedCourses() {
		return repeatedCourses;
	}



	public void setRepeatedCourses(Integer repeatedCourses) {
		this.repeatedCourses = repeatedCourses;
	}



	public SemesterEnum getSemester() {
		return semester;
	}



	public void setSemester(SemesterEnum semester) {
		this.semester = semester;
	}



	public Integer getYear() {
		return year;
	}



	public void setYear(Integer year) {
		this.year = year;
	}



	public Student getStudent() {
		return student;
	}



	public void setStudent(Student student) {
		this.student = student;
	}



	public Double getGpa() {
		return gpa;
	}



	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}



	public Concentration getConcentration() {
		return concentration;
	}



	public void setConcentration(Concentration concentration) {
		this.concentration = concentration;
	}



	public String getMinor() {
		return minor;
	}



	public void setMinor(String minor) {
		this.minor = minor;
	}



	public String getTranscript() {
		return transcript;
	}



	public void setTranscript(String transcript) {
		this.transcript = transcript;
	}



	public Double getAttempt_credit_hours() {
		return attempt_credit_hours;
	}



	public void setAttempt_credit_hours(Double attempt_credit_hours) {
		this.attempt_credit_hours = attempt_credit_hours;
	}
	
	
	
	
}
