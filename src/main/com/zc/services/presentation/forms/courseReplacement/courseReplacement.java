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
	query = "from courseReplacement d where d.studentId.id = :id"
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
	@NamedQuery(name="courseReplacement.getAllRefused",
	query = "from courseReplacement d where d.action = 2"
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

	public static int STEP_AUDITING=0;
	public static int STEP_MajorHead=1;
	public static int STEP_DirectorOfAccredition=2;
	public static int STEP_DeanOfStratigicEnrollment=3;
	public static int STEP_AssociateDean=4;
	public static int STEP_Registerar=5;
	public static int STEP_Finished=6;
	
	
	
	@Column(name = "formStep")
	private Integer formStep;
	

	public static int TYPE_ENGINEERING=1;
	public static int TYPE_SCIENCE=2;
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
	public static int STATE_ACCEPTED=1;
	public static int STATE_REFUSED=2;
	
	
	@Column(name = "action")
	private Integer action;
	
	@Column(name = "submissionDate")
	private Calendar submissionDate;
	

	@Column(name = "lastUpdateDate")
	private Calendar lastUpdateDate;
	

	@Column(name = "auditingDate")
	private Calendar auditingDate;
	
	
	@Column(name = "registerationDate")
	private Calendar registerationDate;
	

	@Column(name = "programDirectorDate")
	private Calendar programDirectorDate;
	

	@Column(name = "viceDirectorDate")
	private Calendar viceDirectorDate;
	

	@Column(name = "deanDate")
	private Calendar deanDate;
	

	@Column(name = "associateDeanDate")
	private Calendar associateDeanDate;


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
	
	
	public String getStepString() {
		if(formStep==STEP_AssociateDean) {
			return "Associate Dean";
		}else if(formStep==STEP_AUDITING) {
			return "Registrar Auditing";
		}else if(formStep==STEP_DeanOfStratigicEnrollment) {
			return "Dean Of Stratigic Enrollment";
		}else if(formStep==STEP_MajorHead) {
			return "Major Head";
		}else if(formStep==STEP_Registerar) {
			return "Registrar";
		}else if(formStep==STEP_Finished) {
			return "Finished";
		}else {
			return "Vice Director Of Accredition";
		}
	}
	
	
	public String getStateString() {
		if(action==STATE_ACCEPTED) {
			return "ACCEPTED";
		}else if(action==STATE_INPROCESS) {
			return "IN PROCESS";
		}else  {
			return "REFUSED";
		}
	}


	
	public Calendar getAuditingDate() {
		return auditingDate;
	}


	public void setAuditingDate(Calendar auditingDate) {
		this.auditingDate = auditingDate;
	}


	public Calendar getRegisterationDate() {
		return registerationDate;
	}


	public void setRegisterationDate(Calendar registerationDate) {
		this.registerationDate = registerationDate;
	}


	public Calendar getProgramDirectorDate() {
		return programDirectorDate;
	}


	public void setProgramDirectorDate(Calendar programDirectorDate) {
		this.programDirectorDate = programDirectorDate;
	}


	public Calendar getViceDirectorDate() {
		return viceDirectorDate;
	}


	public void setViceDirectorDate(Calendar viceDirectorDate) {
		this.viceDirectorDate = viceDirectorDate;
	}


	public Calendar getDeanDate() {
		return deanDate;
	}


	public void setDeanDate(Calendar deanDate) {
		this.deanDate = deanDate;
	}


	public Calendar getAssociateDeanDate() {
		return associateDeanDate;
	}


	public void setAssociateDeanDate(Calendar associateDeanDate) {
		this.associateDeanDate = associateDeanDate;
	}
	
	
	
}
