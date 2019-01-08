/**
 * 
 */
package main.com.zc.services.presentation.forms.courseChangeComfirmation;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Student;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="CCC.getAll",
		     query="SELECT c FROM CCC c"
		     )
	,
	@NamedQuery(name="CCC.getByStudentId",
	query = "from CCC d where d.student.id = :id"
			)
	,
	@NamedQuery(name="CCC.getById",
	query = "from CCC d where d.id = :id"
			)
	,
	@NamedQuery(name="CCC.getByMajorId",
	query = "from CCC d where d.majorId = :id"
			)
	
	,
	@NamedQuery(name="CCC.getAllForStepAndMajorId",
	query = "from CCC d where d.majorId = :id and d.stateStep = :step and action!=2"
			)
	,
	@NamedQuery(name="CCC.getAllForStepAndType",
	query = "from CCC d where d.type = :type and d.stateStep = :step and action!=2"
			)
	
	,
	@NamedQuery(name="CCC.getAllForStep",
	query = "from CCC d where d.stateStep = :step and action!=2"
			)
	
})

@Entity
@Table(name = "coursechangecomfirmation")
public class CCC {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "NewCourseId")
	private Courses newCourse;
	
	@ManyToOne
	@JoinColumn(name = "StudentId")
	private Student student;
	
	@Column(name = "StudentIntake")
	private Integer studentIntake;
	
	@Column(name = "CourseCountAs")
	private Integer courseCountAs;
	
	@Column(name = "majorId")
	private Integer majorId;
	/*
	 * 0 is for major head
	 * 1 is for director of the accredition
	 * 2 is for dean of addmission
	 * 3 is for director of admission
	 * 4 is for registrar staff
	 */
	@Column(name = "stateStep")
	private Integer stateStep;
	
	@Column(name = "type")
	private Integer type;
	
	
	/*
	 * 
	 * 0 in process
	 * 1 accepted
	 * 2 refused
	 */
	@Column(name = "action")
	private Integer action;
	
	@Column(name = "SUBMISSION_DATE")
	private Calendar date;
	
	
	@Column(name = "UPDATE_DATE")
	private Calendar updateDate;
	
	
	@ManyToOne
	@JoinColumn(name = "OldCourseId")
	private Courses courseOld;
	

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getStudentIntake() {
		return studentIntake;
	}

	public void setStudentIntake(Integer studentIntake) {
		this.studentIntake = studentIntake;
	}

	public Integer getCourseCountAs() {
		return courseCountAs;
	}

	public void setCourseCountAs(Integer courseCountAs) {
		this.courseCountAs = courseCountAs;
	}

	public Integer getMajorId() {
		return majorId;
	}

	public void setMajorId(Integer majorId) {
		this.majorId = majorId;
	}

	public Integer getStateStep() {
		return stateStep;
	}

	public void setStateStep(Integer stateStep) {
		this.stateStep = stateStep;
	}

	public Courses getCourseOld() {
		return courseOld;
	}

	public void setCourseOld(Courses courseOld) {
		this.courseOld = courseOld;
	}

	public Courses getNewCourse() {
		return newCourse;
	}

	public void setNewCourse(Courses newCourse) {
		this.newCourse = newCourse;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	
}
