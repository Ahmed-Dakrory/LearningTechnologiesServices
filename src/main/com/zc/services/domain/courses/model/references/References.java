/**
 * 
 */
package main.com.zc.services.domain.courses.model.references;

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
	
	
	@NamedQuery(name="References.getAll",
		     query="SELECT c FROM References c"
		     )
	,
	@NamedQuery(name="References.getByCourseId",
	query = "from References d where d.courseId = :id"
			)
	,
	@NamedQuery(name="References.getById",
	query = "from References d where d.id = :id"
			)
	
})

@Entity
@Table(name = "reference")
public class References {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "COURSE_ID")
	private Integer courseId;
	
	
	@Column(name = "Name")
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
