package main.com.zc.service.instructor_survey_ans;


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

import main.com.zc.service.instructor_survey_ques.instructor_survey_ques;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="instructor_survey_ans.getAll",
		     query="SELECT c FROM instructor_survey_ans c "
		     )
	,
	@NamedQuery(name="instructor_survey_ans.getById",
	query = "from instructor_survey_ans d where d.id = :id "
			)
	,
	@NamedQuery(name="instructor_survey_ans.getAllByCourse",
	query = "from instructor_survey_ans d where d.courseId.id = :courseId"
			)
	,
	@NamedQuery(name="instructor_survey_ans.getAllByCourseAndInstructor",
	query = "from instructor_survey_ans d where d.courseId.id = :courseId and d.instructorId.id = :instructorId"
			)
	
	,
	@NamedQuery(name="instructor_survey_ans.getAllByCourseAndInstructorAndStudent",
	query = "from instructor_survey_ans d where d.courseId.id = :courseId and d.instructorId.id = :instructorId and d.studentId.id = :studentId"
			)
		
	
})

@Entity
@Table(name = "instructor_survey_ans")
public class instructor_survey_ans {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student studentId;
	
	@ManyToOne
	@JoinColumn(name = "courseId")
	private Courses courseId;
	
	
	@ManyToOne
	@JoinColumn(name = "instructorId")
	private Employee instructorId;
	
	
	@ManyToOne
	@JoinColumn(name = "quesId")
	private instructor_survey_ques quesId;
	
	

	@Column(name = "comment")
	private String comment;
	
	

	@Column(name = "ans")
	private Integer ans;
	
	

	@Column(name = "date")
	private Calendar date;



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



	public Courses getCourseId() {
		return courseId;
	}



	public void setCourseId(Courses courseId) {
		this.courseId = courseId;
	}



	public Employee getInstructorId() {
		return instructorId;
	}



	public void setInstructorId(Employee instructorId) {
		this.instructorId = instructorId;
	}



	public instructor_survey_ques getQuesId() {
		return quesId;
	}



	public void setQuesId(instructor_survey_ques quesId) {
		this.quesId = quesId;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public Integer getAns() {
		return ans;
	}



	public void setAns(Integer ans) {
		this.ans = ans;
	}



	public Calendar getDate() {
		return date;
	}



	public void setDate(Calendar date) {
		this.date = date;
	}
	

	
	
}