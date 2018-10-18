/**
 * 
 */
package main.com.zc.services.domain.courses.model.courseIns;

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
	
	
	@NamedQuery(name="CourseInSyllabus.getAll",
		     query="SELECT c FROM CourseInSyllabus c"
		     )
	,
	@NamedQuery(name="CourseInSyllabus.getByCourseId",
	query = "from CourseInSyllabus d where d.courseId = :id"
			)
	,
	@NamedQuery(name="CourseInSyllabus.getById",
	query = "from CourseInSyllabus d where d.id = :id"
			)
	
})

@Entity
@Table(name = "course_ins_syllabus")
public class CourseInSyllabus {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "COURSE_ID")
	private Integer courseId;
	
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Room")
	private String room;
	
	@Column(name = "Building")
	private String building;
	
	@Column(name = "OfficeHours")
	private String officeHours;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(String officeHours) {
		this.officeHours = officeHours;
	}



	

	
	
	
}
