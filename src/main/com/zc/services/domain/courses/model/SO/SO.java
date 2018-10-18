/**
 * 
 */
package main.com.zc.services.domain.courses.model.SO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="SO.getAll",
		     query="SELECT c FROM SO c"
		     )
	,
	@NamedQuery(name="SO.getByCourseId",
	query = "from SO d where d.courseId = :id"
			)
	,
	@NamedQuery(name="SO.getById",
	query = "from SO d where d.id = :id"
			)
	
})

@Entity
@Table(name = "student_outcome")
public class SO {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "COURSE_ID")
	private Integer courseId;
	
	@Column(name = "SO")
	private String so;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getSo() {
		return so;
	}

	public void setSo(String so) {
		this.so = so;
	}

	
}
