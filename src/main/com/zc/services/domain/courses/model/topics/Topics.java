/**
 * 
 */
package main.com.zc.services.domain.courses.model.topics;

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
	
	
	@NamedQuery(name="Topics.getAll",
		     query="SELECT c FROM Topics c"
		     )
	,
	@NamedQuery(name="Topics.getByCourseId",
	query = "from Topics d where d.courseId = :id"
			)
	,
	@NamedQuery(name="Topics.getById",
	query = "from Topics d where d.id = :id"
			)
	
})

@Entity
@Table(name = "topics")
public class Topics {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "CourseId")
	private Integer courseId;
	
	@Column(name = "name")
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
