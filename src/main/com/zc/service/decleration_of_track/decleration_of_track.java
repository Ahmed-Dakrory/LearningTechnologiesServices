package main.com.zc.service.decleration_of_track;


import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.person.model.Student;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="decleration_of_track.getAll",
		     query="SELECT c FROM instructor_all_survey_ques c "
		     )
	,
	@NamedQuery(name="decleration_of_track.getById",
	query = "from decleration_of_track d where d.id = :id "
			)
	,
	@NamedQuery(name="decleration_of_track.getAllByYearAndSemestar",
	query = "from decleration_of_track d where d.year = :year and d.semester = :semester"
			)
	
	,
	@NamedQuery(name="decleration_of_track.getAllByYearAndSemestarAndStudent",
	query = "from decleration_of_track d where d.year = :year and d.semester = :semester and d.studentId.id = :studentId"
			)
	
		
	
})

@Entity
@Table(name = "decleration_of_track")
public class decleration_of_track {

	// Track Engineering  0
	// Track Science  1

	public static Integer ENGINEERING_TRACK = 0;
	public static Integer SCIENCE_TRACK = 1;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "semester")
	private Integer semester;

	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student studentId;
	
	

	@Column(name = "track")
	private Integer track;
	

	@Column(name = "date")
	private Calendar date;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	public Integer getSemester() {
		return semester;
	}


	public void setSemester(Integer semester) {
		this.semester = semester;
	}


	public Student getStudentId() {
		return studentId;
	}


	public void setStudentId(Student studentId) {
		this.studentId = studentId;
	}


	public Integer getTrack() {
		return track;
	}


	public void setTrack(Integer track) {
		this.track = track;
	}


	public Calendar getDate() {
		return date;
	}


	public void setDate(Calendar date) {
		this.date = date;
	}
	

	
	
	
}
