/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.data.model.Courses;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "SkipMajorHeadCourses.getAll", query = "SELECT d FROM SkipMajorHeadCourses d ORDER BY d.id DESC"),
	@NamedQuery(name = "SkipMajorHeadCourses.getById", query = "from SkipMajorHeadCourses d where d.id = :id"),
	@NamedQuery(name = "SkipMajorHeadCourses.getByCourseID", query = "from SkipMajorHeadCourses d where d.course.id = :id")

}
 )
@Entity
@Table(name = "skip_majorHead_courses")
public class SkipMajorHeadCourses {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
    @ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Courses course;

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
    
    
    
}
