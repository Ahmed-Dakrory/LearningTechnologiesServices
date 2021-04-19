/**
 * 
 */
package main.com.zc.services.presentation.forms.change_grade_petition;

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

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;

/**
 * @author dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="change_grade_petition.getAll",
		     query="SELECT c FROM change_grade_petition c"
		     )
	,
	@NamedQuery(name="change_grade_petition.getByStudentId",
	query = "from change_grade_petition d where d.studentId.id = :id"
			)
	,
	@NamedQuery(name="change_grade_petition.getById",
	query = "from change_grade_petition d where d.id = :id"
			)
	,
	@NamedQuery(name="change_grade_petition.getByMajorId",
	query = "from change_grade_petition d where d.majorId = :id"
			)
	
	,
	@NamedQuery(name="change_grade_petition.getAllRefused",
	query = "from change_grade_petition d where d.action = 2"
			)
	
	,
	@NamedQuery(name="change_grade_petition.getAllForStepAndMajorId",
	query = "from change_grade_petition d where d.majorId = :id and d.formStep = :step and action!=2"
			)
	
	
	,
	@NamedQuery(name="change_grade_petition.getAllForStepAndInstructorId",
	query = "from change_grade_petition d where d.courseInstructorId = :id and d.formStep = :step and action!=2"
			)
	
	
	,
	@NamedQuery(name="change_grade_petition.getAllForStep",
	query = "from change_grade_petition d where d.formStep = :step and action!=2"
			)
	
})

@Entity
@Table(name = "change_grade_petition")
public class change_grade_petition {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "questionWhy")
	private String questionWhy;
	

	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student studentId;
	

	@ManyToOne
	@JoinColumn(name = "courseInstructorId")
	private Employee courseInstructorId;
	

	@ManyToOne
	@JoinColumn(name = "courseId")
	private Courses courseId;
	
	
	
	
	
	@Column(name = "registerationComment")
	private String registerationComment;
	
	@Column(name = "programDirectorComment")
	private String programDirectorComment;
	
	@Column(name = "courseInstructorComment")
	private String courseInstructorComment;
	
	@Column(name = "deanComment")
	private String deanComment;
	
	

	public static int STEP_Course_Instructor=0;
	public static int STEP_ProgramHead=1;
	public static int STEP_DeanOfStratigicEnrollment=2;
	public static int STEP_Registerar=3;
	public static int STEP_Finished=4;
	
	
	
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
	
	
	
	@Column(name = "registerationDate")
	private Calendar registerationDate;
	

	@Column(name = "programDirectorDate")
	private Calendar programDirectorDate;
	

	@Column(name = "courseInstructorDate")
	private Calendar courseInstructorDate;
	

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
		if(formStep==STEP_Course_Instructor) {
			return "Course Instructor";
		}else if(formStep==STEP_DeanOfStratigicEnrollment) {
			return "Dean Of Stratigic Enrollment";
		}else if(formStep==STEP_ProgramHead) {
			return "Program Head";
		}else if(formStep==STEP_Registerar) {
			return "Registrar";
		}else{
			return "Finished";
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


	

	public Employee getCourseInstructorId() {
		return courseInstructorId;
	}


	public void setCourseInstructorId(Employee courseInstructorId) {
		this.courseInstructorId = courseInstructorId;
	}


	public String getCourseInstructorComment() {
		return courseInstructorComment;
	}


	public void setCourseInstructorComment(String courseInstructorComment) {
		this.courseInstructorComment = courseInstructorComment;
	}


	public Calendar getCourseInstructorDate() {
		return courseInstructorDate;
	}


	public void setCourseInstructorDate(Calendar courseInstructorDate) {
		this.courseInstructorDate = courseInstructorDate;
	}


	public Courses getCourseId() {
		return courseId;
	}


	public void setCourseId(Courses courseId) {
		this.courseId = courseId;
	}


	

	

	
	
	
}
