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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.data.model.Courses;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "StudentsCoursesNumber.getAll", query = "SELECT d FROM StudentsCoursesNumber d"),
	@NamedQuery(name = "StudentsCoursesNumber.getById", query = "from StudentsCoursesNumber d where d.id = :id"),
	@NamedQuery(name = "StudentsCoursesNumber.getByCourseID", query = "from StudentsCoursesNumber d where d.course.id = :id"),

    }
 )


@Entity
@Table(name = "students_courses_number")
public class StudentsCoursesNumber {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Courses course;
	
	@Column(name = "NUMBER_OF_STUDENTS")
	private Integer num;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}
