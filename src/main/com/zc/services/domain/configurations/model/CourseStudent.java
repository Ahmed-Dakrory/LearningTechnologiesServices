/**
 * 
 */
package main.com.zc.services.domain.configurations.model;

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
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "CourseStudent.getAll", query = "SELECT d FROM CourseStudent d"),
	@NamedQuery(name = "CourseStudent.getById", query = "from CourseStudent d where d.id = :id"),
	@NamedQuery(name = "CourseStudent.getByStudentID", query = "from CourseStudent d where d.student.id = :id"),
	@NamedQuery(name = "CourseStudent.getByCourseID", query = "from CourseStudent d where d.course.id = :id"),
	@NamedQuery(name = "CourseStudent.getByStudentIDAndByCourseID", query = "from CourseStudent d where d.student.id = :studentID And d.course.id = :courseId"),
	@NamedQuery(name = "CourseStudent.getByStudentIDAndSemesterAndYear", query = "from CourseStudent d where d.student.id = :studentID And d.course.semester = :semester" +
			" And d.course.year = :year"),
	@NamedQuery(name = "CourseStudent.getByFacultyIDAndSemesterAndYear", query =
"from CourseStudent d where d.student.fileNo = :facultyID And d.course.semester = :semester" +
			" And d.course.year = :year"),
			
			@NamedQuery(name = "CourseStudent.getBySemesterAndYear", query = "from CourseStudent d where d.course.semester = :semester" +
					" And d.course.year = :year")
    }
 )


@Entity
@Table(name = "courses_student")
public class CourseStudent {

	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT")
	private Student student;
	
	
	@ManyToOne
	@JoinColumn(name = "COURSE")
	private Courses course;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public Courses getCourse() {
		return course;
	}


	public void setCourse(Courses course) {
		this.course = course;
	}
	
	
}
