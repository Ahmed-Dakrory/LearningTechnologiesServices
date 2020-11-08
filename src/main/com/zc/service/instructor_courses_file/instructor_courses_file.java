package main.com.zc.service.instructor_courses_file;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="instructor_courses_file.getAll",
		     query="SELECT c FROM instructor_courses_file c "
		     )
	,
	@NamedQuery(name="instructor_courses_file.getById",
	query = "from instructor_courses_file d where d.id = :id "
			)
	,
	@NamedQuery(name="instructor_courses_file.getAllByInstructorEmailAndYearAndSemester",
	query = "from instructor_courses_file d where d.ins_email = :email and d.semester = :semester and d.year = :year"
			)
	
	
})
 
@Entity
@Table(name = "instructor_courses_file")
public class instructor_courses_file {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;


	@Column(name = "courseCode")
	private String courseCode;
	

	@Column(name = "courseFileLink")
	private String courseFileLink;
	
	

	@Column(name = "ins_name")
	private String ins_name;
	
	@Column(name = "ins_email")
	private String ins_email;
	
	

	@Column(name = "year")
	private String year;
	
	@Column(name = "semester")
	private String semester;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getIns_name() {
		return ins_name;
	}

	public void setIns_name(String ins_name) {
		this.ins_name = ins_name;
	}

	public String getIns_email() {
		return ins_email;
	}

	public void setIns_email(String ins_email) {
		this.ins_email = ins_email;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getCourseFileLink() {
		return courseFileLink;
	}

	public void setCourseFileLink(String courseFileLink) {
		this.courseFileLink = courseFileLink;
	}




	
	
}
