/**
 * 
 */
package main.com.zc.services.presentation.forms.courseReplacement;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.person.model.Student;

/**
 * @author dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="courseReplacement.getAll",
		     query="SELECT c FROM courseReplacement c"
		     )
	,
	@NamedQuery(name="courseReplacement.getByStudentId",
	query = "from courseReplacement d where d.student.id = :id"
			)
	,
	@NamedQuery(name="courseReplacement.getById",
	query = "from courseReplacement d where d.id = :id"
			)
	,
	@NamedQuery(name="courseReplacement.getByMajorId",
	query = "from courseReplacement d where d.majorId = :id"
			)
	
	,
	@NamedQuery(name="courseReplacement.getAllForStepAndMajorId",
	query = "from courseReplacement d where d.majorId = :id and d.formStep = :step and action!=2"
			)
	,
	@NamedQuery(name="courseReplacement.getAllForStepAndType",
	query = "from courseReplacement d where d.type = :type and d.formStep = :step and action!=2"
			)
	
	,
	@NamedQuery(name="courseReplacement.getAllForStep",
	query = "from courseReplacement d where d.formStep = :step and action!=2"
			)
	
})

@Entity
@Table(name = "courseReplacement")
public class courseReplacement {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "courseComplete")
	private String courseComplete;
	
	@Column(name = "toReplace")
	private String toReplace;
	
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student studentId;
	
	@Column(name = "studentInTake")
	private Integer studentInTake;
	
	@Column(name = "courseCountAs")
	private Integer courseCountAs;
	
	@Column(name = "courseCompleteCredits")
	private Integer courseCompleteCredits;
	
	@Column(name = "toReplaceCredits")
	private Integer toReplaceCredits;
	
	
	
	@Column(name = "registerationComment")
	private String registerationComment;
	
	@Column(name = "programDirectorComment")
	private String programDirectorComment;
	
	@Column(name = "viceDirectorComment")
	private String viceDirectorComment;
	
	@Column(name = "deanComment")
	private String deanComment;
	
	@Column(name = "associateDeanComment")
	private String associateDeanComment;
	
	/*
	 * -1 as auditing
	 * 0 is for major head
	 * 1 is for director of the accredition
	 * 2 is for dean of addmission
	 * 3 is for director of admission
	 * 4 is for registrar staff
	 */

	public static int STEP_AUDITING=-1;
	public static int STEP_MajorHead=0;
	public static int STEP_DirectorOfAccredition=1;
	public static int STEP_DeanOfAddmission=2;
	public static int STEP_AssociateDean=3;
	public static int STEP_Registerar=4;
	
	@Column(name = "formStep")
	private Integer formStep;
	
	@Column(name = "type")
	private Integer type;
	
	@Column(name = "majorId")
	private Integer majorId;
	
	
	/*
	 * 
	 * 0 in process
	 * 1 accepted
	 * 2 refused
	 */
	

	public static int STATE_INPROCESS=0;
	public static int STATE_ACCEPTED=0;
	public static int STATE_REFUSED=0;
	
	
	@Column(name = "action")
	private Integer action;
	
	@Column(name = "submissionDate")
	private Calendar submissionDate;
	
	
	@Column(name = "lastUpdateDate")
	private Calendar lastUpdateDate;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCourseComplete() {
		return courseComplete;
	}


	public void setCourseComplete(String courseComplete) {
		this.courseComplete = courseComplete;
	}


	public String getToReplace() {
		return toReplace;
	}


	public void setToReplace(String toReplace) {
		this.toReplace = toReplace;
	}


	public Student getStudentId() {
		return studentId;
	}


	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}


	public Integer getStudentInTake() {
		return studentInTake;
	}


	public void setStudentInTake(Integer studentInTake) {
		this.studentInTake = studentInTake;
	}


	public Integer getCourseCountAs() {
		return courseCountAs;
	}


	public void setCourseCountAs(Integer courseCountAs) {
		this.courseCountAs = courseCountAs;
	}


	public Integer getCourseCompleteCredits() {
		return courseCompleteCredits;
	}


	public void setCourseCompleteCredits(Integer courseCompleteCredits) {
		this.courseCompleteCredits = courseCompleteCredits;
	}


	public Integer getToReplaceCredits() {
		return toReplaceCredits;
	}


	public void setToReplaceCredits(Integer toReplaceCredits) {
		this.toReplaceCredits = toReplaceCredits;
	}


	public String getRegisterationComment() {
		return registerationComment;
	}


	public void setRegisterationComment(String registerationComment) {
		this.registerationComment = registerationComment;
	}


	public String getProgramDirectorComment() {
		return programDirectorComment;
	}


	public void setProgramDirectorComment(String programDirectorComment) {
		this.programDirectorComment = programDirectorComment;
	}


	public String getViceDirectorComment() {
		return viceDirectorComment;
	}


	public void setViceDirectorComment(String viceDirectorComment) {
		this.viceDirectorComment = viceDirectorComment;
	}


	public String getDeanComment() {
		return deanComment;
	}


	public void setDeanComment(String deanComment) {
		this.deanComment = deanComment;
	}


	public String getAssociateDeanComment() {
		return associateDeanComment;
	}


	public void setAssociateDeanComment(String associateDeanComment) {
		this.associateDeanComment = associateDeanComment;
	}


	public Integer getFormStep() {
		return formStep;
	}


	public void setFormStep(Integer formStep) {
		this.formStep = formStep;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getMajorId() {
		return majorId;
	}


	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}


	public Integer getAction() {
		return action;
	}


	public void setAction(Integer action) {
		this.action = action;
	}


	public Calendar getSubmissionDate() {
		return submissionDate;
	}


	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}


	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}


	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	
	
}
