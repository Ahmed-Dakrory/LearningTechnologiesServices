/**
 * 
 */
package main.com.zc.services.domain.courses.model.schedule;

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
	
	
	@NamedQuery(name="Schedule.getAll",
		     query="SELECT c FROM Schedule c"
		     )
	,
	@NamedQuery(name="Schedule.getByCourseId",
	query = "from Schedule d where d.courseId = :id"
			)
	,
	@NamedQuery(name="Schedule.getById",
	query = "from Schedule d where d.id = :id"
			)
	
})

@Entity
@Table(name = "schedule")
public class Schedule {

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "CourseId")
	private Integer courseId;
	
	@Column(name = "Day")
	private String day;
	
	@Column(name = "FromTime")
	private String fromTime;
	
	@Column(name = "ToTime")
	private String toTime;
	
	@Column(name = "Room")
	private String room;
	
	@Column(name = "Building")
	private String building;

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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
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

	
}
