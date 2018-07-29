
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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya.alaa
 *
 */
@NamedQueries({

	@NamedQuery(name = "OverloadRequest.getAll", query = "SELECT d FROM OverloadRequest d ORDER BY d.id DESC"),
	@NamedQuery(name = "OverloadRequest.getById", query = "from OverloadRequest d where d.id = :id"),
	@NamedQuery(name = "OverloadRequest.getByStudentID", query = "from OverloadRequest d where d.student.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "OverloadRequest.getByCourseCoordniator", query = "from OverloadRequest d where d.course.courseCoordinator.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "OverloadRequest.getPendingByPA", query = "from OverloadRequest d where (d.major.headOfMajorId.id= :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "OverloadRequest.getOldByPA", query = "from OverloadRequest d where d.major.headOfMajorId.id= :id or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "OverloadRequest.getDeanPending", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and c.step = 1 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "OverloadRequest.getAdHeadPending", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and ((c.step = 2 and (c.provostRequired = 0 or c.provostRequired IS Null)) or c.step = 5) and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "OverloadRequest.getAdDeptPending", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and c.step = 3 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "OverloadRequest.getProvostPending", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and c.step = 2 and c.provostRequired = 1 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )")
	,@NamedQuery(name = "OverloadRequest.getInstructorPending", query = "SELECT new main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject( c.major.headOfMajorId , COUNT(c)) FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL) and c.step = 0  and ( :forDailyMAil = true  OR c.insNotifyDate IS Null ) GROUP BY c.major.headOfMajorId ")
	,
	@NamedQuery(name = "OverloadRequest.getPendingJob", query = "FROM OverloadRequest c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
	@NamedQuery(name = "OverloadRequest.getHistory", query = "FROM OverloadRequest c where c.performed = 1 and ((:searchType = 2) OR ( ( :searchType = 0 OR c.student.fileNo = :studentId )  and (:searchType = 1 OR c.student.data.NameInEnglish Like :studentName))) ORDER BY c.id DESC"),
	@NamedQuery(name = "OverloadRequest.getOldSpring", query = "FROM OverloadRequest c where c.performed =1 and year(c.submissionDate) = :year " +
			"and ( month(c.submissionDate) = 2 or month(c.submissionDate) = 3 or month(c.submissionDate) = 4 " +
			"or month(c.submissionDate) = 5 or (month(c.submissionDate) = 6 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "OverloadRequest.getOldSummer", query = "FROM OverloadRequest c where c.performed =1 and year(c.submissionDate) = :year " +
			"and (month(c.submissionDate) = 8 or month(c.submissionDate) = 7 " +
			"or (month(c.submissionDate) = 6 and day(c.submissionDate)>15)" +
			" or (month(c.submissionDate) = 9 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "OverloadRequest.getOldFall", query = "FROM OverloadRequest c where c.performed =1 and year(c.submissionDate) = :year " +
									"and ( month(c.submissionDate) = 10 or month(c.submissionDate) = 11 or month(c.submissionDate) = 12 " +
									"or month(c.submissionDate) = 1 or (month(c.submissionDate) = 9 and day(c.submissionDate)>15)) ORDER BY c.id DESC")
	,@NamedQuery(name = "OverloadRequest.getAuditingPetitions", query = "SELECT c FROM OverloadRequest c Where c.step =11 ORDER BY c.id DESC")
	,@NamedQuery(name = "OverloadRequest.getPendingAndAuditing", query = "SELECT d FROM OverloadRequest d where (d.performed = 0 or d.performed IS NULL) and d.step = 3 OR d.step = 11  ORDER BY d.id DESC")
}
 )

@Entity
@Table(name = "overload_request")
public class OverloadRequest {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="PHONE")
	private String mobile;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar submissionDate;
	
	@Column(name = "INS_ACTION_DATE")
	private Calendar insActionDate;
	
	@Column(name = "DEAN_ACTION_DATE")
	private Calendar deanActionDate;
	
	@Column(name = "PROVOST_ACTION_DATE")
	private Calendar provostActionDate;
	
	
	@Column(name = "ADMISSION_HEAD_DATE")
	private Calendar admissionHeadActionDate;
	
	
	@Column(name = "ADMISSION_DEPT_DATE")
	private Calendar admissionDeptActionDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachments attachments;
	
	
	@Column(name = "PROVOST_REQUIRED")
	private Boolean  provostRequired;
	
	

	@Column(name = "step")
	private PetitionStepsEnum step;
	
	@Column(name="PERFORMED")
	private Boolean performed;
	
	@Column(name="REASON")
	private String reason;
	
	@Column(name="ACADEMIC_YEAR")
	private String year;
	
	@Column(name="INS_NOTIFY_DATE")
	private Date insNotifyDate;
	
	@Column(name="INS_SEND_MAIL")
	private Boolean insSendMail;
	
	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Courses course;

	@Column(name = "GPA")
	private String Gpa;
	
	@Column(name="COMMENT")
	private String comment;
	
	@ManyToOne
	@JoinColumn(name="MAJOR")
	private Majors major;

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
	
	public OverloadRequest() {
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public Calendar getInsActionDate() {
		return insActionDate;
	}

	public void setInsActionDate(Calendar insActionDate) {
		this.insActionDate = insActionDate;
	}

	public Calendar getDeanActionDate() {
		return deanActionDate;
	}

	public void setDeanActionDate(Calendar deanActionDate) {
		this.deanActionDate = deanActionDate;
	}

	public Calendar getProvostActionDate() {
		return provostActionDate;
	}

	public void setProvostActionDate(Calendar provostActionDate) {
		this.provostActionDate = provostActionDate;
	}

	public Calendar getAdmissionHeadActionDate() {
		return admissionHeadActionDate;
	}

	public void setAdmissionHeadActionDate(Calendar admissionHeadActionDate) {
		this.admissionHeadActionDate = admissionHeadActionDate;
	}

	public Calendar getAdmissionDeptActionDate() {
		return admissionDeptActionDate;
	}

	public void setAdmissionDeptActionDate(Calendar admissionDeptActionDate) {
		this.admissionDeptActionDate = admissionDeptActionDate;
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

	public String getGPA() {
		return Gpa;
	}

	public Boolean getProvostRequired() {
		return provostRequired;
	}

	public void setProvostRequired(Boolean provostRequired) {
		this.provostRequired = provostRequired;
	}
	
	public void setGPA(String gpa) {
		this.Gpa = gpa;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getGpa() {
		return Gpa;
	}

	public void setGpa(String gpa) {
		Gpa = gpa;
	}

	public Majors getMajor() {
		return major;
	}

	public void setMajor(Majors major) {
		this.major = major;
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
