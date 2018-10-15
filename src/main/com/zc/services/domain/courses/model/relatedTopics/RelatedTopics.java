/**
 * 
 */
package main.com.zc.services.domain.courses.model.relatedTopics;

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
	
	
	@NamedQuery(name="RelatedTopics.getAll",
		     query="SELECT c FROM RelatedTopics c"
		     )
	,
	@NamedQuery(name="RelatedTopics.getByCourseId",
	query = "from RelatedTopics d where d.courseId = :id"
			)
	
})

@Entity
@Table(name = "related_topics")
public class RelatedTopics {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "Course_ID")
	private Integer courseId;
	
	
	@Column(name = "Related_topic")
	private String relatedTopics;


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


	public String getRelatedTopics() {
		return relatedTopics;
	}


	public void setRelatedTopics(String relatedTopics) {
		this.relatedTopics = relatedTopics;
	}
	

	


	

	
	
	
}
