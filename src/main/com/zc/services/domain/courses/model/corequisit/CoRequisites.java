/**
 * 
 */
package main.com.zc.services.domain.courses.model.corequisit;

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
	
	
	@NamedQuery(name="CoRequisites.getAll",
		     query="SELECT c FROM CoRequisites c"
		     )
	,
	@NamedQuery(name="CoRequisites.getByCourseId",
	query = "from CoRequisites d where d.courseId = :id"
			)
	
})

@Entity
@Table(name = "co_requisites")
public class CoRequisites {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "Course_ID")
	private Integer courseId;
	
	
	@Column(name = "Co_Requisite")
	private String coRequisite;


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


	public String getCoRequisite() {
		return coRequisite;
	}


	public void setCoRequisite(String coRequisite) {
		this.coRequisite = coRequisite;
	}


	
	
	
}
