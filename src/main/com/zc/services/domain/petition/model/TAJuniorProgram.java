/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Student;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "TAJuniorProgram.getAll", query = "SELECT d FROM TAJuniorProgram d ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getById", query = "from TAJuniorProgram d where d.id = :id"),
	@NamedQuery(name = "TAJuniorProgram.getByCourseID", query = "from TAJuniorProgram d where d.course.id = :id"),
	@NamedQuery(name = "TAJuniorProgram.getByStudentID", query = "from TAJuniorProgram d where d.student.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getByCourseCoordniatorPending", query = "from TAJuniorProgram d where d.course.courseCoordinator.id = :id AND ( d.step = 0 or d.step = 1) ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getByCourseCoordniatorOld", query = "from TAJuniorProgram d where d.course.courseCoordinator.id = :id AND " +
			"((d.step = 7 OR d.step = 2) or d.performed= 1) ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getPendingByPA", query = "from TAJuniorProgram d where d.major.headOfMajorId.id= :id AND(d.step = 1 OR d.step = 7) ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getOldByPA", query = "from TAJuniorProgram d where d.major.headOfMajorId.id= :id AND ((d.step = 7 OR d.step = 2)or d.performed= 1) ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getPendingByDean", query = "from TAJuniorProgram d where d.performed= 0 AND (d.step = 7 OR d.step = 2) ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getPendingByDeanDashboard", query = "from TAJuniorProgram d where d.performed is null AND (d.step = 7) ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getPendingByCoordDashboard", query = "from TAJuniorProgram d where d.course.courseCoordinator.id = :id AND ( d.step = 0) ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getPendingByPADashboard", query = "from TAJuniorProgram d where d.major.headOfMajorId.id= :id AND(d.step = 1) ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getOldByDean", query = "from TAJuniorProgram d where d.performed= 1 ORDER BY d.id DESC"),
	@NamedQuery(name = "TAJuniorProgram.getPendingJob", query = "FROM TAJuniorProgram d where (d.performed = 0 or d.performed IS NULL)  "
			+ "and  d.insNotifyDate IS Not Null and d.insSendMail IS NULL"),
	/*
	@NamedQuery(name = "TAJuniorProgram.getDeanPending", query = "FROM TAJuniorProgram c where c.performed = 0 c.step = 1 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "OverloadRequest.getAdHeadPending", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and ((c.step = 2 and (c.provostRequired = 0 or c.provostRequired IS Null)) or c.step = 5) and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "OverloadRequest.getAdDeptPending", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and c.step = 3 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "OverloadRequest.getProvostPending", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and c.step = 2 and c.provostRequired = 1 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )")
	,@NamedQuery(name = "OverloadRequest.getInstructorPending", query = "SELECT new main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject( c.major.headOfMajorId , COUNT(c)) FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and c.step = 0  and ( :forDailyMAil = true  OR c.insNotifyDate IS Null ) GROUP BY c.major.headOfMajorId ")
	,
	@NamedQuery(name = "OverloadRequest.getPendingJob", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
	@NamedQuery(name = "OverloadRequest.getHistory", query = "FROM OverloadRequest c where c.performed = 1 and ((:searchType = 2) OR ( ( :searchType = 0 OR c.student.fileNo = :studentId )  and (:searchType = 1 OR c.student.data.NameInEnglish Like :studentName))) ORDER BY c.id DESC")
	*/
}
 )
@Entity
@Table(name = "ta_junior_request")
public class TAJuniorProgram {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

    @ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Courses course;
    
	@Column(name = "GPA")
	private Double Gpa;
	
	@Column(name="HOURS")
	private Double hours;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
	
	@Column(name = "STEP")
	private PetitionStepsEnum step;
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar submissionDate;
	
	@Column(name="COMMENT")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "MAJOR")
	private Majors major;
	
	@Column(name="PERFORMED")
	private Boolean performed;

	@Column(name="INS_NOTIFY_DATE")
	private Date insNotifyDate;
	
	@Column(name="INS_SEND_MAIL")
	private Boolean insSendMail;
	
	@Column(name="GRADE")
	private String grade;
	
	@Column(name="TASK_INS")
	private String task;
	
	@Column(name="HOURS_INS")
	private Double hoursINS;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public Double getGpa() {
		return Gpa;
	}

	public void setGpa(Double gpa) {
		Gpa = gpa;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public PetitionStepsEnum getStep() {
		return step;
	}

	public void setStep(PetitionStepsEnum step) {
		this.step = step;
	}

	public Calendar getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Majors getMajor() {
		return major;
	}

	public void setMajor(Majors major) {
		this.major = major;
	}

	public Boolean getPerformed() {
		return performed;
	}

	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}

	public Date getInsNotifyDate() {
		return insNotifyDate;
	}

	public void setInsNotifyDate(Date insNotifyDate) {
		this.insNotifyDate = insNotifyDate;
	}

	public Boolean getInsSendMail() {
		return insSendMail;
	}

	public void setInsSendMail(Boolean insSendMail) {
		this.insSendMail = insSendMail;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Double getHoursINS() {
		return hoursINS;
	}

	public void setHoursINS(Double hoursINS) {
		this.hoursINS = hoursINS;
	}



	
}
