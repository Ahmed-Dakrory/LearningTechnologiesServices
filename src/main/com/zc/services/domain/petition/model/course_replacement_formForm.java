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
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;

/**
 * @author omnya.alaa
 * @author Eman
 */
@NamedQueries({

	@NamedQuery(name = "course_replacement_formForm.getAll", query = "SELECT d FROM course_replacement_formForm d ORDER BY d.id DESC"),
	@NamedQuery(name = "course_replacement_formForm.getById", query = "from course_replacement_formForm d where d.id = :id"),
	@NamedQuery(name = "course_replacement_formForm.getByStudentID", query = "from course_replacement_formForm d where d.student.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "course_replacement_formForm.getPendingByPA", query = "from course_replacement_formForm d where ( d.forwardTOIns IS NULL) or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "course_replacement_formForm.getOldByPA", query = "from course_replacement_formForm d where  d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "course_replacement_formForm.getDeanPending", query = "FROM course_replacement_formForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 1 and  ( :forDailyMAil = true   OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "course_replacement_formForm.getDeanAcadPending", query = "FROM course_replacement_formForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 1 and  ( :forDailyMAil = true   OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "course_replacement_formForm.getAccredEngPending", query = "FROM course_replacement_formForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 2 and  ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "course_replacement_formForm.getAccredSciPending", query = "FROM course_replacement_formForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 2 and  ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "course_replacement_formForm.getInstructorPending", query = "SELECT new main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject( c.newMajor.headOfMajorId , COUNT(c)) FROM course_replacement_formForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 1  and ( :forDailyMAil = true  OR c.insNotifyDate IS Null ) GROUP BY c.newMajor.headOfMajorId  "),
	@NamedQuery(name = "course_replacement_formForm.getAdDeptPending", query = "FROM course_replacement_formForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 3 and  ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "course_replacement_formForm.getPendingJob", query = "FROM course_replacement_formForm c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
	@NamedQuery(name = "course_replacement_formForm.getHistory", query = "FROM course_replacement_formForm c where c.performed = 1 and ((:searchType = 2) OR ( ( :searchType = 0 OR c.student.fileNo = :studentId )  and (:searchType = 1 OR c.student.data.NameInEnglish Like :studentName))) ORDER BY c.id DESC"),
	@NamedQuery(name = "course_replacement_formForm.getOldSpring", query = "FROM course_replacement_formForm c where c.performed =1 and year(c.submissionDate) = :year " +
			"and ( month(c.submissionDate) = 2 or month(c.submissionDate) = 3 or month(c.submissionDate) = 4 " +
			"or month(c.submissionDate) = 5 or (month(c.submissionDate) = 6 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "course_replacement_formForm.getOldSummer", query = "FROM course_replacement_formForm c where c.performed =1 and year(c.submissionDate) = :year " +
			"and (month(c.submissionDate) = 8 or month(c.submissionDate) = 7 " +
			"or (month(c.submissionDate) = 6 and day(c.submissionDate)>15)" +
			" or (month(c.submissionDate) = 9 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "course_replacement_formForm.getOldFall", query = "FROM course_replacement_formForm c where c.performed =1 and year(c.submissionDate) = :year " +
									"and ( month(c.submissionDate) = 10 or month(c.submissionDate) = 11 or month(c.submissionDate) = 12 " +
									"or month(c.submissionDate) = 1 or (month(c.submissionDate) = 9 and day(c.submissionDate)>15)) ORDER BY c.id DESC"),
}
 )


@Entity
@Table(name = "course_replacement_form")
public class course_replacement_formForm {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name = "science_or_engineering")
	private Integer science_or_engineering;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachments attachments;
	
	
	@Column(name="toReplaceCourse")
	private String toReplaceCourse;
	
	@Column(name="courseFinished")
	private String courseFinished;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar submissionDate;

	@Column(name = "step")
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
	@JoinColumn(name = "FORWARD_TO_INS")
	private Employee forwardTOIns;
	
	@ManyToOne
	@JoinColumn(name = "FORWARD_FROM_INS")
	private Employee forwardFromIns;
	
	
	@Column(name = "FORWARD_HISTORY")
	private String forwardHistory;
	

	@Column(name="REVERTED")
	private Boolean reverted;
	
	@ManyToOne
	@JoinColumn(name = "NEW_MAJOR")
	private Majors newMajor;
	

	public static Integer SCIENCE_UNIT_ACCEDITION = 0;
	public static Integer ENGINEERING_UNIT_ACCEDITION = 1;
	
	public course_replacement_formForm() {
		super();
	}

	
	
	public Integer getScience_or_engineering() {
		return science_or_engineering;
	}



	public void setScience_or_engineering(Integer science_or_engineering) {
		this.science_or_engineering = science_or_engineering;
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

	


	public String getToReplaceCourse() {
		return toReplaceCourse;
	}

	public void setToReplaceCourse(String toReplaceCourse) {
		this.toReplaceCourse = toReplaceCourse;
	}

	public String getCourseFinished() {
		return courseFinished;
	}

	public void setCourseFinished(String courseFinished) {
		this.courseFinished = courseFinished;
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

	public Attachments getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachments attachments) {
		this.attachments = attachments;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
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

	public Majors getNewMajor() {
		return newMajor;
	}

	public void setNewMajor(Majors newMajor) {
		this.newMajor = newMajor;
	}

	

	
}
