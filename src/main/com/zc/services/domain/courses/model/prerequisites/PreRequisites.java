/**
 * 
 */
package main.com.zc.services.domain.courses.model.prerequisites;

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
	
	
	@NamedQuery(name="PreRequisites.getAll",
		     query="SELECT c FROM PreRequisites c"
		     )
	,
	@NamedQuery(name="PreRequisites.getByCourseId",
	query = "from PreRequisites d where d.courseId = :id"
			)
	,
	@NamedQuery(name="PreRequisites.getById",
	query = "from PreRequisites d where d.id = :id"
			)
	
})

@Entity
@Table(name = "pre_requisites")
public class PreRequisites {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "Course_ID")
	private Integer courseId;
	
	
	@Column(name = "Prerequisite")
	private String preRequisite;


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


	public String getPreRequisite() {
		return preRequisite;
	}


	public void setPreRequisite(String preRequisite) {
		this.preRequisite = preRequisite;
	}


	


	
	
	
}
