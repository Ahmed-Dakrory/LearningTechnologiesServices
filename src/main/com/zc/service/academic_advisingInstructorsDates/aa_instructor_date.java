package main.com.zc.service.academic_advisingInstructorsDates;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.service.academic_advising_instructor.aa_instructor;
import main.com.zc.service.academic_advising_student_profile.aa_student_profile;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="aa_instructor_date.getAll",
		     query="SELECT c FROM aa_instructor_date c "
		     )
	,
	@NamedQuery(name="aa_instructor_date.getById",
	query = "from aa_instructor_date d where d.id = :id "
			)
	,
	@NamedQuery(name="aa_instructor_date.getByInstructorIdAndYearAndSemester",
	query = "from aa_instructor_date d where d.instructor.id = :id and d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	,
	@NamedQuery(name="aa_instructor_date.getByInstructorIdAndStudentIdAndYearAndSemester",
	query = "from aa_instructor_date d where d.instructor.id = :instructorId and d.student.id = :studentId and d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	,
	@NamedQuery(name="aa_instructor_date.getByStudentIdAndYearAndSemester",
	query = "from aa_instructor_date d where  d.student.id = :id and d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	,
	@NamedQuery(name="aa_instructor_date.getByActionAndYearAndSemester",
	query = "from aa_instructor_date d where d.state = :state and d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	
	,
	@NamedQuery(name="aa_instructor_date.getByActionAndInstructorAndYearAndSemester",
	query = "from aa_instructor_date d where d.state = :state and d.instructor.id = :id and d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	
	,
	@NamedQuery(name="aa_instructor_date.getAllAvailableByInstructorIdAndYearAndSemester",
	query = "from aa_instructor_date d where d.state is null and d.instructor.id = :id and d.year = :year and Lower(d.semester) = Lower(:semester) and d.date > CURRENT_DATE"
			)
	,
	@NamedQuery(name="aa_instructor_date.getAllByYearAndSemester",
	query = "from aa_instructor_date d where d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	
	
	
})
 
@Entity
@Table(name = "taa_instructor_date")
public class aa_instructor_date {

	public static String State_Reserved = "Reserved";
	public static String State_Cancelled_by_Instructor = "Cancelled by Instructor";
	public static String State_Cancelled_by_Student = "Cancelled by Student";
	public static String State_Finished = "Finished";
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "student")
	private aa_student_profile student;
	
	
	@ManyToOne
	@JoinColumn(name = "instructor")
	private aa_instructor instructor;
	
	
	@Column(name = "year")
	private String year;
	

	@Column(name = "semester")
	private String semester;
	
	

	@Column(name = "state")
	private String state;
	
	

	

	@Column(name = "date")
	private Date date;



	

	@Column(name = "studentMessege")
	private String studentMessege;
	

	@Column(name = "instructorMessege")
	private String instructorMessege;
	

	@Column(name = "comments")
	private String comments;
	

	@Column(name = "recommendations")
	private String recommendations;


	@Column(name = "dateStudentLastAction")
	private Date dateStudentLastAction;
	
	

	@Column(name = "datelastComment")
	private Date datelastComment;
	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public aa_student_profile getStudent() {
		return student;
	}



	public void setStudent(aa_student_profile student) {
		this.student = student;
	}



	public aa_instructor getInstructor() {
		return instructor;
	}



	public void setInstructor(aa_instructor instructor) {
		this.instructor = instructor;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getSemester() {
		return semester;
	}



	public void setSemester(String semester) {
		this.semester = semester;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}





	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getStudentMessege() {
		return studentMessege;
	}



	public void setStudentMessege(String studentMessege) {
		this.studentMessege = studentMessege;
	}



	public String getInstructorMessege() {
		return instructorMessege;
	}



	public void setInstructorMessege(String instructorMessege) {
		this.instructorMessege = instructorMessege;
	}



	public String getComments() {
		return comments;
	}



	public void setComments(String comments) {
		this.comments = comments;
	}



	public String getRecommendations() {
		return recommendations;
	}



	public void setRecommendations(String recommendations) {
		this.recommendations = recommendations;
	}



	public Date getDateStudentLastAction() {
		return dateStudentLastAction;
	}



	public void setDateStudentLastAction(Date dateStudentLastAction) {
		this.dateStudentLastAction = dateStudentLastAction;
	}



	public Date getDatelastComment() {
		return datelastComment;
	}



	public void setDatelastComment(Date datelastComment) {
		this.datelastComment = datelastComment;
	}



	
	
}
