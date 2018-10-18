/**
 * 
 */
package main.com.zc.services.domain.courses.model.clo;

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
	
	
	@NamedQuery(name="CLO.getAll",
		     query="SELECT c FROM CLO c"
		     )
	,
	@NamedQuery(name="CLO.getByCourseId",
	query = "from CLO d where d.courseId = :id"
			)
	,
	@NamedQuery(name="CLO.getById",
	query = "from CLO d where d.id = :id"
			)
	
})

@Entity
@Table(name = "course_learning_objectives")
public class CLO {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "COURSE_ID")
	private Integer courseId;
	
	@Column(name = "CLO")
	private String clo;

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

	public String getClo() {
		return clo;
	}

	public void setClo(String clo) {
		this.clo = clo;
	}
	
	
	
}
