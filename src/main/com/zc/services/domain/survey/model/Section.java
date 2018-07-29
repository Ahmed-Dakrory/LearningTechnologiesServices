/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.data.model.Courses;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "Section.getAll", query = "SELECT s FROM Section s"),
	@NamedQuery(name = "Section.getById", query = "from Section s where s.id = :id "),
	@NamedQuery(name = "Section.getByIdAndCourseID", query = "from Section s where s.id = :id and s.course.id= :cId"),
	@NamedQuery(name = "Section.getByCourseID", query = "from Section s where s.course.id= :id")
	})


@Entity
@Table(name="sections")
public class Section {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name="NAME")
	private String name;
	@Column(name="CREATION_DATE")
	private Date date;
	
	//COURSE_ID
	@JoinColumn(name="COURSE_ID")
	@ManyToOne
	private Courses  course;

	@Column(name="TITLE")
	private String title;
	
	@Column(name="EXPORT_STYLE")
	private Integer exportStyle;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Courses getCourse() {
		return course;
	}
	public void setCourse(Courses course) {
		this.course = course;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getExportStyle() {
		return exportStyle;
	}
	public void setExportStyle(Integer exportStyle) {
		this.exportStyle = exportStyle;
	}

	
	
}
