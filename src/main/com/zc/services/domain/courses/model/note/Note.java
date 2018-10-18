/**
 * 
 */
package main.com.zc.services.domain.courses.model.note;

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
	
	
	@NamedQuery(name="Note.getAll",
		     query="SELECT c FROM SO c"
		     )
	,
	@NamedQuery(name="Note.getByCourseId",
	query = "from Note d where d.courseId = :id"
             )
	,
	@NamedQuery(name="Note.getById",
	query = "from Note d where d.id = :id"
			)
})

@Entity
@Table(name = "notes")
public class Note {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "Course_ID")
	private Integer courseId;
	
	@Column(name = "Note")
	private String note;

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
}
