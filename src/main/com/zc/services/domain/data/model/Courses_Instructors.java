/**
 * 
 */
package main.com.zc.services.domain.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.person.model.Employee;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Omnya Alaa
 * 
 */
@NamedQueries({
		@NamedQuery(name = "Courses_Instructors.getAll", query = "SELECT ci FROM Courses_Instructors ci"),
		@NamedQuery(name = "Courses_Instructors.getById", query = "from Courses_Instructors ci where ci.id = :id"),
		@NamedQuery(name = "Courses_Instructors.getByCourseId", query = "from Courses_Instructors ci where ci.course.id = :courseID and ci.instructor.type =1"),
		@NamedQuery(name = "Courses_Instructors.getByInstructorId", query = "from Courses_Instructors ci where ci.instructor.id = :instructorID") ,
		@NamedQuery(name = "Courses_Instructors.getByInstructorIdAndYearAndSemester", query = "from Courses_Instructors ci where ci.instructor.id = :instructorID"
				+ " and ci.course.semester = :semester and ci.course.year = :year") ,
		@NamedQuery(name = "Courses_Instructors.getByInstructorMail", query = "from Courses_Instructors ci where ci.instructor.mail = :mail"),
		@NamedQuery(name = "Courses_Instructors.getByCourseIdAndInsId", query = "from Courses_Instructors ci where ci.course.id = :courseID and ci.instructor.id = :instructorID"),
		@NamedQuery(name = "Courses_Instructors.getTAsByCourseID", query = "from Courses_Instructors ci where ci.course.id = :courseID and ci.instructor.type =2"),
		@NamedQuery(name = "Courses_Instructors.getInstructorsByCourseID", query = "from Courses_Instructors ci where ci.course.id = :courseID and ci.instructor.type =1")})

@Entity
@Table(name = "courses_instructors")

public class Courses_Instructors {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	  @ManyToOne
	  @JoinColumn(name="COURSE_ID")
	  private Courses course;
	  
	  @ManyToOne
	  @JoinColumn(name="INSTRUCTOR_ID")
	  private Employee instructor;

	public Courses_Instructors(int id, Courses course, Employee instructor) {
		super();
		this.id = id;
		this.course = course;
		this.instructor = instructor;
	}

	public Courses_Instructors() {
		super();
	}

	public Courses_Instructors(Courses course, Employee instructor) {
		super();
		this.course = course;
		this.instructor = instructor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public Employee getInstructor() {
		return instructor;
	}

	public void setInstructor(Employee instructor) {
		this.instructor = instructor;
	}

}
