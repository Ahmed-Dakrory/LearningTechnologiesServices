/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({
	@NamedQuery(name = "IncompleteGrade.getAll", query = "SELECT d FROM IncompleteGrade d ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getById", query = "from IncompleteGrade d where d.id = :id"),
	@NamedQuery(name = "IncompleteGrade.getByStudentID", query = "from IncompleteGrade d where d.student.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getPendingByInstructor", query = "from IncompleteGrade d where (d.instructor.id = :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getOldByInstructor", query = "from IncompleteGrade d where d.instructor.id = :id or d.forwardTOIns.id =:id  ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getPendingByPA", query = "from IncompleteGrade d where (d.major.headOfMajorId.id= :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getOldByPA", query = "from IncompleteGrade d where d.major.headOfMajorId.id= :id or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getPendingJob", query = "FROM IncompleteGrade c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
	@NamedQuery(name = "IncompleteGrade.getOldSpring", query = "FROM IncompleteGrade c where c.performed =1 and year(c.submissionDate) = :year " +
			"and ( month(c.submissionDate) = 2 or month(c.submissionDate) = 3 or month(c.submissionDate) = 4 " +
			"or month(c.submissionDate) = 5 or (month(c.submissionDate) = 6 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getOldSummer", query = "FROM IncompleteGrade c where c.performed =1 and year(c.submissionDate) = :year " +
			"and (month(c.submissionDate) = 8 or month(c.submissionDate) = 7 " +
			"or (month(c.submissionDate) = 6 and day(c.submissionDate)>15)" +
			" or (month(c.submissionDate) = 9 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "IncompleteGrade.getOldFall", query = "FROM IncompleteGrade c where c.performed =1 and year(c.submissionDate) = :year " +
									"and ( month(c.submissionDate) = 10 or month(c.submissionDate) = 11 or month(c.submissionDate) = 12 " +
									"or month(c.submissionDate) = 1 or (month(c.submissionDate) = 9 and day(c.submissionDate)>15)) ORDER BY c.id DESC"),
	})
@Entity
@Table(name = "incomplete_grade")
public class IncompleteGrade {

	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="MOBILE")
	private String mobile;
	
	@Column(name = "STEP")
	private PetitionStepsEnum step;
	
	@Column(name="PERFORMED")
	private Boolean performed;

	@Column(name="INS_NOTIFY_DATE")
	private Date insNotifyDate;
	
	@Column(name="INS_SEND_MAIL")
	private Boolean insSendMail;
	
	@Column(name="COMMENT")
	private String comment;	
	
	@ManyToOne
	@JoinColumn(name = "STUDENT")
	private Student student;
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar submissionDate;
	
	@Column(name = "DATE_OF_EXAM")
	private Calendar dateOfExam;
	
	
	@ManyToOne
	@JoinColumn(name = "MAJOR")
	private Majors major;
	
	
	@ManyToOne
	@JoinColumn(name = "COURSE")
	private Courses course;
	
	
	@ManyToOne
	@JoinColumn(name = "INSTRUCTOR")
	private Employee instructor;
	
	
	
	@Column(name = "INCOMPLETE_REASON")
	private String reason;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachments attachments;

	
	@ManyToOne
	@JoinColumn(name = "FORWARD_TO_INS")
	private Employee forwardTOIns;
	
	@ManyToOne
	@JoinColumn(name = "FORWARD_FROM_INS")
	private Employee forwardFromIns;
	
	
	@Column(name = "FORWARD_HISTORY")
	private String forwardHistory;
	
	@Column(name = "SEMESTER")
	private SemesterEnum semester;

	@Column(name="REVERTED")
	private Boolean reverted;
	
	public IncompleteGrade() {
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public PetitionStepsEnum getStep() {
		return step;
	}


	public void setStep(PetitionStepsEnum step) {
		this.step = step;
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


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public Calendar getSubmissionDate() {
		return submissionDate;
	}


	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}


	public Majors getMajor() {
		return major;
	}


	public void setMajor(Majors major) {
		this.major = major;
	}


	public Courses getCourse() {
		return course;
	}


	public void setCourse(Courses course) {
		this.course = course;
	}


	public Employee getInstructor() {
		return instructor;
	}


	public void setInstructor(Employee instructor) {
		this.instructor = instructor;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public Attachments getAttachments() {
		return attachments;
	}


	public void setAttachments(Attachments attachments) {
		this.attachments = attachments;
	}


	public Employee getForwardTOIns() {
		return forwardTOIns;
	}


	public void setForwardTOIns(Employee forwardTOIns) {
		this.forwardTOIns = forwardTOIns;
	}


	public Employee getForwardFromIns() {
		return forwardFromIns;
	}


	public void setForwardFromIns(Employee forwardFromIns) {
		this.forwardFromIns = forwardFromIns;
	}


	public SemesterEnum getSemester() {
		return semester;
	}


	public void setSemester(SemesterEnum semester) {
		this.semester = semester;
	}


	public Calendar getDateOfExam() {
		return dateOfExam;
	}


	public void setDateOfExam(Calendar dateOfExam) {
		this.dateOfExam = dateOfExam;
	}


	public String getForwardHistory() {
		return forwardHistory;
	}


	public void setForwardHistory(String forwardHistory) {
		this.forwardHistory = forwardHistory;
	}


	public Boolean getReverted() {
		return reverted;
	}


	public void setReverted(Boolean reverted) {
		this.reverted = reverted;
	}


	
}
