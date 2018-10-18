/**
 * 
 */
package main.com.zc.services.domain.courses.model.grades;

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
	
	
	@NamedQuery(name="Grade.getAll",
		     query="SELECT c FROM Grade c"
		     )
	,
	@NamedQuery(name="Grade.getByCourseId",
	query = "from Grade d where d.courseId = :id"
			)
	,
	@NamedQuery(name="Grade.getById",
	query = "from Grade d where d.id = :id"
			)
	
})

@Entity
@Table(name = "grading")
public class Grade {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "Course_ID")
	private Integer courseId;
	
	@Column(name = "TypeOfGrade")
	private String typeOfGrade;
	
	@Column(name = "Presentage")
	private Integer presentage;

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

	public String getTypeOfGrade() {
		return typeOfGrade;
	}

	public void setTypeOfGrade(String typeOfGrade) {
		this.typeOfGrade = typeOfGrade;
	}

	public Integer getPresentage() {
		return presentage;
	}

	public void setPresentage(Integer presentage) {
		this.presentage = presentage;
	}
	


	
	
	
}
