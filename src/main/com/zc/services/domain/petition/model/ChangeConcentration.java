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
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.survey.model.Concentration;

/**
 * @author omnya
 *
 */

@NamedQueries({

	@NamedQuery(name = "ChangeConcentration.getAll", query = "SELECT d FROM ChangeConcentration d ORDER BY d.id DESC"),
	@NamedQuery(name = "ChangeConcentration.getById", query = "from ChangeConcentration d where d.id = :id"),
	@NamedQuery(name = "ChangeConcentration.getByStudentID", query = "from ChangeConcentration d where d.student.id = :id ORDER BY d.id DESC"),
	@NamedQuery(name = "ChangeConcentration.getPendingByPA", query = "from ChangeConcentration d where (d.major.headOfMajorId.id = :id and d.forwardTOIns IS NULL) or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "ChangeConcentration.getOldByPA", query = "from ChangeConcentration d where d.major.headOfMajorId.id= :id or d.forwardTOIns.id =:id ORDER BY d.id DESC"),
	@NamedQuery(name = "ChangeConcentration.getAdHeadPending", query = "FROM ChangeConcentration c where (c.performed = 0 or c.performed IS NULL) and c.step = 8 and  ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "ChangeConcentration.getAdHeadPendingNow", query = "FROM ChangeConcentration c where (c.performed = 0 or c.performed IS NULL) and c.step = 8 "),
	@NamedQuery(name = "ChangeConcentration.getAdDeptPending", query = "FROM ChangeConcentration c where (c.performed = 0 or c.performed IS NULL) and c.step = 5 and  ( :forDailyMAil = true  OR c.insNotifyDate IS Null )"),
	@NamedQuery(name = "ChangeConcentration.getAdDeptPendingNoReminder", query = "FROM ChangeConcentration c where (c.performed = 0 or c.performed IS NULL) and c.step = 3 "),
	@NamedQuery(name = "ChangeConcentration.getStudentPending", query = "FROM ChangeConcentration c where (c.performed = 0 or c.performed IS NULL) and c.student.id = :id"),
	@NamedQuery(name = "ChangeConcentration.getInstructorPending", query = "SELECT new main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject( c.major.headOfMajorId , COUNT(c)) FROM ChangeConcentration c where (c.performed = 0 or c.performed IS NULL) and c.step = 0  and ( :forDailyMAil = true  OR c.insNotifyDate IS Null ) GROUP BY c.major.headOfMajorId  "),
	@NamedQuery(name = "ChangeConcentration.getPendingJob", query = "FROM ChangeConcentration c where (c.performed = 0 or c.performed IS NULL)  and  c.insNotifyDate IS Not Null and c.insSendMail IS NULL"),
	@NamedQuery(name = "ChangeConcentration.getHistory", query = "FROM ChangeConcentration c where c.performed = 1 and ((:searchType = 2) OR ( ( :searchType = 0 OR c.student.fileNo = :studentId )  and (:searchType = 1 OR c.student.data.NameInEnglish Like :studentName))) ORDER BY c.id DESC"),
	@NamedQuery(name = "ChangeConcentration.getOldSpring", query = "FROM ChangeConcentration c where c.performed =1 and year(c.submissionDate) = :year " +
			"and ( month(c.submissionDate) = 2 or month(c.submissionDate) = 3 or month(c.submissionDate) = 4 " +
			"or month(c.submissionDate) = 5 or (month(c.submissionDate) = 6 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "ChangeConcentration.getOldSummer", query = "FROM ChangeConcentration c where c.performed =1 and year(c.submissionDate) = :year " +
					"and (month(c.submissionDate) = 8 or month(c.submissionDate) = 7 " +
					"or (month(c.submissionDate) = 6 and day(c.submissionDate)>15)" +
					" or (month(c.submissionDate) = 9 and day(c.submissionDate)<16)) ORDER BY c.id DESC"),
	@NamedQuery(name = "ChangeConcentration.getOldFall", query = "FROM ChangeConcentration c where c.performed =1 and year(c.submissionDate) = :year " +
									"and ( month(c.submissionDate) = 10 or month(c.submissionDate) = 11 or month(c.submissionDate) = 12 " +
									"or month(c.submissionDate) = 1 or (month(c.submissionDate) = 9 and day(c.submissionDate)>15)) ORDER BY c.id DESC"),
}						
 )

// -------------------------------- PA >>>>>> Admission Head >>>>> Registrar---------------------------------------------
@Entity
@Table(name = "change_concentration")
public class ChangeConcentration {

	
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "MAJOR")
	private Majors major;
	
	@ManyToOne
	@JoinColumn(name = "CURRENT_CONCEN")
	private Concentration currentConcen;
		
	@ManyToOne
	@JoinColumn(name = "NEW_CONCEN")
	private Concentration newConcen;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
		
	@Column(name = "STEP")
	private PetitionStepsEnum step;
	
	@Column(name = "PERFORMED")
	private Boolean performed;
	
	@Column(name="INS_NOTIFY_DATE")
	private Date insNotifyDate;
	
	@Column(name="INS_SEND_EMAIL")
	private Boolean insSendMail;
		
	@Column(name="MORE_DETAILS")
	private String moreDetails;
	
	@ManyToOne
	@JoinColumn(name = "FORWARD_TO_INS")
	private Employee forwardTOIns;
	
	@ManyToOne
	@JoinColumn(name = "FORWARD_FROM_INS")
	private Employee forwardFromIns;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ATTACHMENT_ID")
	private Attachments attachments;
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar submissionDate;

	@Column(name = "FORWARD_HISTORY")
	private String forwardHistory;
	

	@Column(name="REVERTED")
	private Boolean reverted;
	
	@Transient
	private Integer month;
	
	@Transient
	private Integer day;
	
	
	@Transient
	private Integer year;
	
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

	public Concentration getCurrentConcen() {
		return currentConcen;
	}

	public void setCurrentConcen(Concentration currentConcen) {
		this.currentConcen = currentConcen;
	}

	public Concentration getNewConcen() {
		return newConcen;
	}

	public void setNewConcen(Concentration newConcen) {
		this.newConcen = newConcen;
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

	public String getMoreDetails() {
		
		return moreDetails;
	}

	public void setMoreDetails(String moreDetails) {
		this.moreDetails = moreDetails;
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

	public Attachments getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachments attachments) {
		this.attachments = attachments;
	}

	public Calendar getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getForwardHistory() {
		return forwardHistory;
	}

	public void setForwardHistory(String forwardHistory) {
		this.forwardHistory = forwardHistory;
	}

	public Integer getMonth() {
		return getSubmissionDate().get(Calendar.MONTH);
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return getSubmissionDate().get(Calendar.DAY_OF_MONTH);
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getYear() {
		return getSubmissionDate().get(Calendar.YEAR);
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getReverted() {
		return reverted;
	}

	public void setReverted(Boolean reverted) {
		this.reverted = reverted;
	}
	
	
}
