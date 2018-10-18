/**
 * 
 */
package main.com.zc.services.domain.courses.model.books;

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
	
	
	@NamedQuery(name="CourseBooks.getAll",
		     query="SELECT c FROM CourseBooks c"
		     )
	,
	@NamedQuery(name="CourseBooks.getByCourseId",
	query = "from CourseBooks d where d.courseId = :id"
			)
	,
	@NamedQuery(name="CourseBooks.getById",
	query = "from CourseBooks d where d.id = :id"
			)
	
})

@Entity
@Table(name = "course_books")
public class CourseBooks {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "Course_ID")
	private Integer courseId;
	
	@Column(name = "Book")
	private String book;

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

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}
	

	
	
	
}
