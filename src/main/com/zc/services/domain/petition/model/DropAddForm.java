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
import main.com.zc.services.domain.shared.enumurations.AddDropFormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.DropTypesEnum;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "DropAddForm.getAll", query = "SELECT d FROM DropAddForm d ORDER BY d.id DESC"),
	@NamedQuery(name = "DropAddForm.getById", query = "from DropAddForm d where d.id = :id"),
	@NamedQuery(name = "DropAddForm.getByStudentID", query = "from DropAddForm d where d.student.id = :id ORDER BY d.id DESC"),
/*	@NamedQuery(name = "DropAddForm.getByCourseCoordinatorID", query = "from DropAddForm d where d.course.courseCoordinator.id= :id"),
	@NamedQuery(name = "DropAddForm.getByCourseID", query = "from DropAddForm d where d.course.id= :id"),
	*/@NamedQuery(name = "DropAddForm.getPendingByPA", query = "from DropAddForm d where (d.major.headOfMajorId.id= :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "DropAddForm.getOldByPA", query = "from DropAddForm d where (((d.droppedCourseIns.id IS NULL and d.major.headOfMajorId.id= :id) or"
			+ " (d.droppedCourseIns.id IS NOT NULL and d.droppedCourseIns.id= :id)) and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "DropAddForm.getDeanPending", query = "FROM DropAddForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 1 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "DropAddForm.getAdHeadPending", query = "FROM DropAddForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 2 and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "DropAddForm.getAdDeptPending", query = "FROM DropAddForm c where (c.performed = 0 or c.performed IS NULL) and (c.step = 3 OR c.step = 9 OR c.step = 1) and ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
/*	@NamedQuery(name = "DropAddForm.getInstructorPending", query = "SELECT new main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject"
			+ "( c.major.headOfMajorId , COUNT(c)) FROM DropAddForm c where (c.performed = 0 or c.performed IS NULL) "
			+ "and c.step = 0  and ( :forDailyMAil = true  OR c.insNotifyDate IS Null ) GROUP BY c.major.headOfMajorId "),*/
	//@NamedQuery(name = "DropAddForm.getInstructorPending", query = "SELECT new main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject
			//( c.droppedCourseIns , COUNT(c)) FROM DropAddForm c where (c.performed = 0 or c.performed IS NULL) and c.step = 0  and 
			//( :forDailyMAil = true  OR c.insNotifyDate IS Null ) GROUP BY c.droppedCourseIns "),
	@NamedQuery(name = "DropAddForm.getPendingJob", query = "FROM DropAddForm c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
	@NamedQuery(name = "DropAddForm.getHistory", query = "FROM DropAddForm c where c.performed = 1 and ((:searchType = 2) OR ( ( :searchType = 0 OR c.student.fileNo = :studentId )  and (:searchType = 1 OR c.student.data.NameInEnglish Like :studentName))) ORDER BY c.id DESC"),
	@NamedQuery(name = "DropAddForm.getByDropppedCourseIns", query = "from DropAddForm d where ((d.step = 0 or d.step = 1) and ((((d.droppedCourseIns.id IS NULL and d.major.headOfMajorId.id= :id) or"
					+ " (d.droppedCourseIns.id IS NOT NULL and d.droppedCourseIns.id= :id)) and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id)) ORDER BY d.id DESC"),
	@NamedQuery(name = "DropAddForm.getPendingDean", query = "from DropAddForm d where (d.step = 2 or d.step = 1)  ORDER BY d.id DESC"),

	@NamedQuery(name = "DropAddForm.getInstructorCountPending", query = "from DropAddForm d where (d.step = 0  and ((((d.droppedCourseIns.id IS NULL and d.major.headOfMajorId.id= :id) or"
					+ " (d.droppedCourseIns.id IS NOT NULL and d.droppedCourseIns.id= :id)) and d.forwardTOIns IS NULL) or d.forwardTOIns.id = :id)) ORDER BY d.id DESC"),
	@NamedQuery(name = "DropAddForm.getOldSpring", query = "FROM DropAddForm c where c.performed =1 and year(c.submissionDate) = :year " +
			"and ( month(c.submissionDate) = 2 or month(c.submissionDate) = 3 or month(c.submissionDate) = 4 " +
			"or month(c.submissionDate) = 5 or (month(c.submissionDate) = 6 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "DropAddForm.getOldSummer", query = "FROM DropAddForm c where c.performed =1 and year(c.submissionDate) = :year " +
			"and (month(c.submissionDate) = 8 or month(c.submissionDate) = 7 " +
			"or (month(c.submissionDate) = 6 and day(c.submissionDate)>15)" +
			" or (month(c.submissionDate) = 9 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "DropAddForm.getOldFall", query = "FROM DropAddForm c where c.performed =1 and year(c.submissionDate) = :year " +
									"and ( month(c.submissionDate) = 10 or month(c.submissionDate) = 11 or month(c.submissionDate) = 12 " +
									"or month(c.submissionDate) = 1 or (month(c.submissionDate) = 9 and day(c.submissionDate)>15)) ORDER BY c.id DESC"),

}
 )

@Entity
@Table(name = "drop_add_form")
public class DropAddForm {

	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	@Column(name="STATUS")
	private String status;
	

	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachments attachments;
	
	@Column(name="TYPE")
	private AddDropFormTypesEnum type;
	
	
	@Column(name="PERFORMED")
	private Boolean performed;
	
	
	@ManyToOne
	@JoinColumn(name = "MAJOR_ID")
	private Majors major;
	
	@ManyToOne
	@JoinColumn(name = "ADDED_COURSE_ID")
	private Courses addedCourse;
	
	@ManyToOne
	@JoinColumn(name = "DROPPPED_COURSE_ID")
	private Courses droppedCourse;
	
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar submissionDate;

	@Column(name = "step")
	private PetitionStepsEnum step;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="INS_NOTIFY_DATE")
	private Date insNotifyDate;
	
	@Column(name="INS_SEND_MAIL")
	private Boolean insSendMail;
	
	@Column(name="COMMENT")
	private String comment;
	
	@Column(name="ADDED_SECTION")
	private String addedSection;
	
	@Column(name="DROPPED_SECTION")
	private String droppedSection;

	@ManyToOne
	@JoinColumn(name="DROPPED_COURSE_INS")
	private Employee droppedCourseIns;
	
	@Column(name="TYPE_OF_DROP")
	private DropTypesEnum dropType;
	
	@ManyToOne
	@JoinColumn(name = "FORWARD_TO_INS")
	private Employee forwardTOIns;
	
	@ManyToOne
	@JoinColumn(name = "FORWARD_FROM_INS")
	private Employee forwardFromIns;
	
	
	@Column(name = "FORWARD_HISTORY")
	private String forwardHistory;
	
	@Column(name="IS_REPEATED_COURSE")
	private Boolean repeatedCourse;
	
	@Column(name="COURSE_LAB")
	private String courseLab;
	
	@Column(name="REVERTED")
	private Boolean reverted;
	
	
	public DropAddForm() {
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


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	


	public AddDropFormTypesEnum getType() {
		return type;
	}


	public void setType(AddDropFormTypesEnum type) {
		this.type = type;
	}


	public Boolean getPerformed() {
		return performed;
	}


	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}


	public Majors getMajor() {
		return major;
	}


	public void setMajor(Majors major) {
		this.major = major;
	}


		public Courses getAddedCourse() {
		return addedCourse;
	}


	public void setAddedCourse(Courses addedCourse) {
		this.addedCourse = addedCourse;
	}


	public Courses getDroppedCourse() {
		return droppedCourse;
	}


	public void setDroppedCourse(Courses droppedCourse) {
		this.droppedCourse = droppedCourse;
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


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
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


	public String getAddedSection() {
		return addedSection;
	}


	public void setAddedSection(String addedSection) {
		this.addedSection = addedSection;
	}


	public String getDroppedSection() {
		return droppedSection;
	}


	public void setDroppedSection(String droppedSection) {
		this.droppedSection = droppedSection;
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


	public Employee getDroppedCourseIns() {
		return droppedCourseIns;
	}


	public void setDroppedCourseIns(Employee droppedCourseIns) {
		this.droppedCourseIns = droppedCourseIns;
	}


	public DropTypesEnum getDropType() {
		return dropType;
	}


	public void setDropType(DropTypesEnum dropType) {
		this.dropType = dropType;
	}


	public Boolean getRepeatedCourse() {
		return repeatedCourse;
	}


	public void setRepeatedCourse(Boolean repeatedCourse) {
		this.repeatedCourse = repeatedCourse;
	}


	public String getCourseLab() {
		return courseLab;
	}


	public void setCourseLab(String courseLab) {
		this.courseLab = courseLab;
	}


	public Boolean getReverted() {
		return reverted;
	}


	public void setReverted(Boolean reverted) {
		this.reverted = reverted;
	}
	
	
}
