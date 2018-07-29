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

	@NamedQuery(name = "ChangeMajorForm.getAll", query = "SELECT d FROM ChangeMajorForm d ORDER BY d.id DESC"),
	@NamedQuery(name = "ChangeMajorForm.getById", query = "from ChangeMajorForm d where d.id = :id"),
	@NamedQuery(name = "ChangeMajorForm.getByStudentID", query = "from ChangeMajorForm d where d.student.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "ChangeMajorForm.getPendingByPA", query = "from ChangeMajorForm d where (d.newMajor.headOfMajorId.id = :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "ChangeMajorForm.getOldByPA", query = "from ChangeMajorForm d where d.newMajor.headOfMajorId.id= :id or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "ChangeMajorForm.getDeanPending", query = "FROM ChangeMajorForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 1 and  ( :forDailyMAil = true   OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "ChangeMajorForm.getAdHeadPending", query = "FROM ChangeMajorForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 2 and  ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "ChangeMajorForm.getAdDeptPending", query = "FROM ChangeMajorForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 3 and  ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "ChangeMajorForm.getInstructorPending", query = "SELECT new main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject( c.newMajor.headOfMajorId , COUNT(c)) FROM ChangeMajorForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 0  and ( :forDailyMAil = true  OR c.insNotifyDate IS Null ) GROUP BY c.newMajor.headOfMajorId  "),
	@NamedQuery(name = "ChangeMajorForm.getPendingJob", query = "FROM ChangeMajorForm c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
	@NamedQuery(name = "ChangeMajorForm.getHistory", query = "FROM ChangeMajorForm c where c.performed = 1 and ((:searchType = 2) OR ( ( :searchType = 0 OR c.student.fileNo = :studentId )  and (:searchType = 1 OR c.student.data.NameInEnglish Like :studentName))) ORDER BY c.id DESC"),
	@NamedQuery(name = "ChangeMajorForm.getOldSpring", query = "FROM ChangeMajorForm c where c.performed =1 and year(c.submissionDate) = :year " +
			"and ( month(c.submissionDate) = 2 or month(c.submissionDate) = 3 or month(c.submissionDate) = 4 " +
			"or month(c.submissionDate) = 5 or (month(c.submissionDate) = 6 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "ChangeMajorForm.getOldSummer", query = "FROM ChangeMajorForm c where c.performed =1 and year(c.submissionDate) = :year " +
			"and (month(c.submissionDate) = 8 or month(c.submissionDate) = 7 " +
			"or (month(c.submissionDate) = 6 and day(c.submissionDate)>15)" +
			" or (month(c.submissionDate) = 9 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "ChangeMajorForm.getOldFall", query = "FROM ChangeMajorForm c where c.performed =1 and year(c.submissionDate) = :year " +
									"and ( month(c.submissionDate) = 10 or month(c.submissionDate) = 11 or month(c.submissionDate) = 12 " +
									"or month(c.submissionDate) = 1 or (month(c.submissionDate) = 9 and day(c.submissionDate)>15)) ORDER BY c.id DESC"),
}
 )


@Entity
@Table(name = "change_major")
public class ChangeMajorForm {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="MOBILE")
	private String mobile;
	
	@Column(name="GPA")
	private Double gpa;
	
	/*@ManyToOne
	@JoinColumn(name = "MAJOR_ID")
	private Majors major;*/
	
	@ManyToOne
	@JoinColumn(name = "CURRENT_MAJOR")
	private Majors curMajor;
	
	@ManyToOne
	@JoinColumn(name = "NEW_MAJOR")
	private Majors newMajor;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachments attachments;
	
	@Column(name="CURRENT_SPEC")
	private String curSpecialization;
	
	@Column(name="NEW_SPEC")
	private String newSpecialization;
	
	@Column(name="DOUBLE_SPEC")
	private String doubleSpecialization;
	
	@Column(name="MORE_DETAILS")
	private String moreDetails;
	
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
	
	
	public ChangeMajorForm() {
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

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	/*public Majors getMajor() {
		return major;
	}

	public void setMajor(Majors major) {
		this.major = major;
	}*/

	public Majors getCurMajor() {
		return curMajor;
	}

	public void setCurMajor(Majors curMajor) {
		this.curMajor = curMajor;
	}

	public Majors getNewMajor() {
		return newMajor;
	}

	public void setNewMajor(Majors newMajor) {
		this.newMajor = newMajor;
	}

	
	public String getCurSpecialization() {
		return curSpecialization;
	}

	public void setCurSpecialization(String curSpecialization) {
		this.curSpecialization = curSpecialization;
	}

	public String getNewSpecialization() {
		return newSpecialization;
	}

	public void setNewSpecialization(String newSpecialization) {
		this.newSpecialization = newSpecialization;
	}

	public String getDoubleSpecialization() {
		return doubleSpecialization;
	}

	public void setDoubleSpecialization(String doubleSpecialization) {
		this.doubleSpecialization = doubleSpecialization;
	}

	public String getMoreDetails() {
		return moreDetails;
	}

	public void setMoreDetails(String moreDetails) {
		this.moreDetails = moreDetails;
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

	

	
}
