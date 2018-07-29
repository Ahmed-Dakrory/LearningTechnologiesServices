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
 * @author Omnya Alaa
 * 
 */
@NamedQueries({

		@NamedQuery(name = "CoursePetition.getAll", query = "SELECT c FROM CoursePetition c ORDER BY c.id DESC"),
		@NamedQuery(name = "CoursePetition.getById", query = "from CoursePetition c where c.id = :id"),
		@NamedQuery(name = "CoursePetition.getByPersonID", query = "from CoursePetition c where c.person.id = :id ORDER BY c.id DESC"),
		@NamedQuery(name = "CoursePetition.getByStatus", query = "from CoursePetition c where c.status = :status ORDER BY c.id DESC"),
		@NamedQuery(name = "CoursePetition.getByDate", query = "from CoursePetition c where c.submittedDate = :submittedDate"),
		@NamedQuery(name = "CoursePetition.getByCourseID", query = "from CoursePetition c where c.course.id = :id ORDER BY c.id DESC"), 
		@NamedQuery(name = "CoursePetition.getByCourseCoordinatorIDPending", query = "from CoursePetition c where (c.course.courseCoordinator.id = :id and c.forwardTOIns IS NULL) or c.forwardTOIns.id = :id  ORDER BY c.id DESC"),
		@NamedQuery(name = "CoursePetition.getByCourseCoordinatorIDOld", query = "from CoursePetition c where c.course.courseCoordinator.id = :id  or c.forwardTOIns.id =:id  ORDER BY c.id DESC"),
		@NamedQuery(name = "CoursePetition.getDeanPending", query = "FROM CoursePetition c where (c.performed = 0 or c.performed IS NULL) and c.step = 1 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
		@NamedQuery(name = "CoursePetition.getAdHeadPending", query = "FROM CoursePetition c where (c.performed = 0 or c.performed IS NULL) and c.step = 2 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
		@NamedQuery(name = "CoursePetition.getAdDeptPending", query = "FROM CoursePetition c where (c.performed = 0 or c.performed IS NULL) and c.step = 3  and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
		@NamedQuery(name = "CoursePetition.getInstructorPending", query = "SELECT new main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject( c.course.courseCoordinator , COUNT(c)) FROM CoursePetition c where (c.performed = 0 or c.performed IS NULL) and c.step = 0  and ( :forDailyMAil = true  OR c.insNotifyDate IS Null ) GROUP BY c.course.courseCoordinator "),
		@NamedQuery(name = "CoursePetition.getPendingJob", query = "FROM CoursePetition c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
		@NamedQuery(name = "CoursePetition.getHistory", query = "FROM CoursePetition c where c.performed = 1 and ((:searchType = 2) OR ( ( :searchType = 0 OR c.person.fileNo = :studentId )  and (:searchType = 1 OR c.person.data.NameInEnglish Like :studentName))) ORDER BY c.id DESC"),
	
		@NamedQuery(name = "CoursePetition.getOldSpring", query = "FROM CoursePetition c where c.performed =1 and year(c.submittedDate) = :year " +
				"and ( month(c.submittedDate) = 2 or month(c.submittedDate) = 3 or month(c.submittedDate) = 4 " +
				"or month(c.submittedDate) = 5 or (month(c.submittedDate) = 6 and day(c.submittedDate)<16)) ORDER BY c.id DESC"),
		@NamedQuery(name = "CoursePetition.getOldSummer", query = "FROM CoursePetition c where c.performed =1 and year(c.submittedDate) = :year " +
				"and (month(c.submittedDate) = 8 or month(c.submittedDate) = 7 " +
				"or (month(c.submittedDate) = 6 and day(c.submittedDate)>15)" +
				" or (month(c.submittedDate) = 9 and day(c.submittedDate)<16)) ORDER BY c.id DESC"),
		@NamedQuery(name = "CoursePetition.getOldFall", query = "FROM CoursePetition c where c.performed =1 and year(c.submittedDate) = :year " +
										"and ( month(c.submittedDate) = 10 or month(c.submittedDate) = 11 or month(c.submittedDate) = 12 " +
										"or month(c.submittedDate) = 1 or (month(c.submittedDate) = 9 and day(c.submittedDate)>15)) ORDER BY c.id DESC"),
		@NamedQuery(name = "CoursePetition.getAuditingPetitions", query = "SELECT c FROM CoursePetition c Where c.step =11 ORDER BY c.id DESC")
		,@NamedQuery(name = "CoursePetition.getPendingAndAuditing", query = "SELECT d FROM CoursePetition d where (d.performed = 0 or d.performed IS NULL) and d.step = 3 OR d.step = 11  ORDER BY d.id DESC")
		}
		)

@Entity
@Table(name = "course_petition")
public class CoursePetition {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "PERSON_ID")
	private Student person;

	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Courses course;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachments attachments;
	
	@Column(name = "DATE_OF_SUBMISSION")
	private Calendar submittedDate;

	@Column(name = "DATE_OF_RESPONDING")
	private Calendar respondingDate;
	
	@Column(name="MOBILE_NO")
	private String mobileNo;

	@Column(name = "REQUEST_TEXT")
	private String requestText;

	@Column(name = "STATUS")
	private String status;

	@Column(name="PERFORMED")
	private Boolean performed;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name = "step")
	private PetitionStepsEnum step;
	
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
	
	public CoursePetition() {
		super();
	}

	public CoursePetition(Integer id, Student person, Courses course,
			Calendar submittedDate, String mobileNo, String requestText) {
		super();
		this.id = id;
		this.person = person;
		this.course = course;
		this.submittedDate = submittedDate;
		this.mobileNo = mobileNo;
		this.requestText = requestText;
	}

	public CoursePetition(Student person, Courses course,
			Calendar submittedDate, String mobileNo, String requestText) {
		super();
		this.person = person;
		this.course = course;
		this.submittedDate = submittedDate;
		this.mobileNo = mobileNo;
		this.requestText = requestText;
	}

	public CoursePetition(Student person, Courses course,
			Calendar submittedDate, String mobileNo, String requestText,
			String status) {
		super();
		this.person = person;
		this.course = course;
		this.submittedDate = submittedDate;
		this.mobileNo = mobileNo;
		this.requestText = requestText;
		this.status = status;
	}

	public CoursePetition(Student person, Courses course,
			Calendar submittedDate, Calendar respondingDate, String mobileNo,
			String requestText, String status) {
		super();
		this.person = person;
		this.course = course;
		this.submittedDate = submittedDate;
		this.respondingDate = respondingDate;
		this.mobileNo = mobileNo;
		this.requestText = requestText;
		this.status = status;
	}

	
	public CoursePetition(Integer id, Student person, Courses course,
			Calendar submittedDate, Calendar respondingDate, String mobileNo,
			String requestText, String status, Boolean editable) {
		super();
		this.id = id;
		this.person = person;
		this.course = course;
		this.submittedDate = submittedDate;
		this.respondingDate = respondingDate;
		this.mobileNo = mobileNo;
		this.requestText = requestText;
		this.status = status;
		//this.editable = editable;
	}

	
	public CoursePetition(Student person, Courses course,
			Calendar submittedDate, Calendar respondingDate, String mobileNo,
			String requestText, String status, Boolean editable) {
		super();
		this.person = person;
		this.course = course;
		this.submittedDate = submittedDate;
		this.respondingDate = respondingDate;
		this.mobileNo = mobileNo;
		this.requestText = requestText;
		this.status = status;
		//this.editable = editable;
	}

	
	public CoursePetition(Integer id, Student person, Courses course,
			Calendar submittedDate, Calendar respondingDate, String mobileNo,
			String requestText, String status, Boolean editable,
			Boolean performed) {
		super();
		this.id = id;
		this.person = person;
		this.course = course;
		this.submittedDate = submittedDate;
		this.respondingDate = respondingDate;
		this.mobileNo = mobileNo;
		this.requestText = requestText;
		this.status = status;
		//this.editable = editable;
		this.performed = performed;
	}

	public CoursePetition(Student person, Courses course,
			Calendar submittedDate, Calendar respondingDate, String mobileNo,
			String requestText, String status, Boolean editable,
			Boolean performed) {
		super();
		this.person = person;
		this.course = course;
		this.submittedDate = submittedDate;
		this.respondingDate = respondingDate;
		this.mobileNo = mobileNo;
		this.requestText = requestText;
		this.status = status;
		//this.editable = editable;
		this.performed = performed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getPerson() {
		return person;
	}

	public void setPerson(Student person) {
		this.person = person;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public Calendar getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Calendar submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getRequestText() {
		return requestText;
	}

	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Calendar getRespondingDate() {
		return respondingDate;
	}

	public void setRespondingDate(Calendar respondingDate) {
		this.respondingDate = respondingDate;
	}

	/*public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}*/

	public Boolean getPerformed() {
		return performed;
	}

	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PetitionStepsEnum getStep() {
		return step;
	}

	public void setStep(PetitionStepsEnum step) {
		this.step = step;
	}

	public Attachments getAttachments() {
		return attachments;
	}
	
	public void setAttachments(Attachments attachments) {
		this.attachments = attachments;
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
