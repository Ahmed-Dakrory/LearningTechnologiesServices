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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author omnya
 * @author Eman
 */
@NamedQueries({

	@NamedQuery(name = "RepeatCourseForm.getAll", query = "SELECT d FROM RepeatCourseForm d ORDER BY d.id DESC"),
	@NamedQuery(name = "RepeatCourseForm.getById", query = "from RepeatCourseForm d where d.id = :id "),
	@NamedQuery(name = "RepeatCourseForm.getByStudentID", query = "from RepeatCourseForm d where d.student.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "RepeatCourseForm.getPendinByPA", query = "from RepeatCourseForm d where (d.major.headOfMajorId.id= :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "RepeatCourseForm.getOldByPA", query = "from RepeatCourseForm d where d.major.headOfMajorId.id= :id or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "RepeatCourseForm.getPendingJob", query = "FROM RepeatCourseForm c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
	@NamedQuery(name = "RepeatCourseForm.getHistory", query = "FROM RepeatCourseForm c where c.performed = 1 and ((:searchType = 2) OR ( ( :searchType = 0 OR c.student.fileNo = :studentId )  and (:searchType = 1 OR c.student.data.NameInEnglish Like :studentName))) ORDER BY c.id DESC"),
	@NamedQuery(name = "RepeatCourseForm.getOldSpring", query = "FROM RepeatCourseForm c where c.performed =1 and year(c.submissionDate) = :year " +
			"and ( month(c.submissionDate) = 2 or month(c.submissionDate) = 3 or month(c.submissionDate) = 4 " +
			"or month(c.submissionDate) = 5 or (month(c.submissionDate) = 6 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "RepeatCourseForm.getOldSummer", query = "FROM RepeatCourseForm c where c.performed =1 and year(c.submissionDate) = :year " +
			"and (month(c.submissionDate) = 8 or month(c.submissionDate) = 7 " +
			"or (month(c.submissionDate) = 6 and day(c.submissionDate)>15)" +
			" or (month(c.submissionDate) = 9 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "RepeatCourseForm.getOldFall", query = "FROM RepeatCourseForm c where c.performed =1 and year(c.submissionDate) = :year " +
									"and ( month(c.submissionDate) = 10 or month(c.submissionDate) = 11 or month(c.submissionDate) = 12 " +
									"or month(c.submissionDate) = 1 or (month(c.submissionDate) = 9 and day(c.submissionDate)>15)) ORDER BY c.id DESC")
	,@NamedQuery(name = "RepeatCourseForm.getAuditingPetitions", query = "SELECT c FROM RepeatCourseForm c Where c.step =11 ORDER BY c.id DESC")
	,@NamedQuery(name = "RepeatCourseForm.getPendingAndAuditing", query = "SELECT d FROM RepeatCourseForm d where (d.performed = 0 or d.performed IS NULL) and d.step = 3 OR d.step = 11  ORDER BY d.id DESC")
	})

@Entity
@Table(name = "course_repeat_form")
public class RepeatCourseForm {

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
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar submissionDate;
	
	
	@ManyToOne
	@JoinColumn(name = "MAJOR_ID")
	private Majors major;
	
	
	@Column(name = "GRADE")
	private String grade;
	
	
	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Courses course;
	
	@Column(name = "REASON")
	private String reason;
	
	
	@Column(name = "OLD_SEMESTER")
	private SemesterEnum oldSem;
	
	
	@Column(name = "NEW_SEMESTER")
	private SemesterEnum newSem;
	
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
	
	@Column(name="REVERTED")
	private Boolean reverted;
	
	public RepeatCourseForm() {
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

	

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	

	public SemesterEnum getOldSem() {
		return oldSem;
	}

	public void setOldSem(SemesterEnum oldSem) {
		this.oldSem = oldSem;
	}

	public SemesterEnum getNewSem() {
		return newSem;
	}

	public void setNewSem(SemesterEnum newSem) {
		this.newSem = newSem;
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
