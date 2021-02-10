/**
 * 
 */
package main.com.zc.services.presentation.forms.gap_form;

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
	
	
	@NamedQuery(name="gap_form.getAll",
		     query="SELECT c FROM gap_form c"
		     )
	,
	@NamedQuery(name="gap_form.getByStudentId",
	query = "from gap_form d where d.studentId.id = :id"
			)
	,
	@NamedQuery(name="gap_form.getById",
	query = "from gap_form d where d.id = :id"
			)
	,
	@NamedQuery(name="gap_form.getByMajorId",
	query = "from gap_form d where d.majorId = :id"
			)
	
	,
	@NamedQuery(name="gap_form.getAllRefused",
	query = "from gap_form d where d.action = 2"
			)
	
	,
	@NamedQuery(name="gap_form.getAllForStepAndMajorId",
	query = "from gap_form d where d.majorId = :id and d.formStep = :step and action!=2"
			)
	
	
	,
	@NamedQuery(name="gap_form.getAllForStep",
	query = "from gap_form d where d.formStep = :step and action!=2"
			)
	
})

@Entity
@Table(name = "gap_form")
public class gap_form {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "questionWhy")
	private String questionWhy;
	
	
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student studentId;
	
	
	
	
	
	@Column(name = "registerationComment")
	private String registerationComment;
	
	@Column(name = "programDirectorComment")
	private String programDirectorComment;
	
	@Column(name = "financeComment")
	private String financeComment;
	
	@Column(name = "deanComment")
	private String deanComment;
	
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
	public static int STEP_Finance=2;
	public static int STEP_DeanOfStratigicEnrollment=3;
	public static int STEP_Registerar=4;
	public static int STEP_Finished=5;
	
	
	
	@Column(name = "formStep")
	private Integer formStep;
	

	
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
	

	@Column(name = "financeDate")
	private Calendar financeDate;
	

	@Column(name = "deanDate")
	private Calendar deanDate;
	



	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}



	public Student getStudentId() {
		return studentId;
	}


	public void setStudentId(Student studentId) {
		this.studentId = studentId;
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


	public String getfinanceComment() {
		return financeComment;
	}


	public void setfinanceComment(String financeComment) {
		this.financeComment = financeComment;
	}


	public String getDeanComment() {
		return deanComment;
	}


	public void setDeanComment(String deanComment) {
		this.deanComment = deanComment;
	}



	public Integer getFormStep() {
		return formStep;
	}


	public void setFormStep(Integer formStep) {
		this.formStep = formStep;
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
		if(formStep==STEP_AUDITING) {
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
			return "Finance";
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




	public Calendar getDeanDate() {
		return deanDate;
	}


	public void setDeanDate(Calendar deanDate) {
		this.deanDate = deanDate;
	}


	public String getQuestionWhy() {
		return questionWhy;
	}


	public void setQuestionWhy(String questionWhy) {
		this.questionWhy = questionWhy;
	}


	public String getFinanceComment() {
		return financeComment;
	}


	public void setFinanceComment(String financeComment) {
		this.financeComment = financeComment;
	}


	public Calendar getFinanceDate() {
		return financeDate;
	}


	public void setFinanceDate(Calendar financeDate) {
		this.financeDate = financeDate;
	}


	
	
	
}
