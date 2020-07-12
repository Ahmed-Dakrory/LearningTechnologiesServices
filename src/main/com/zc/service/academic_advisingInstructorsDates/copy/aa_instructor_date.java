package main.com.zc.service.academic_advisingInstructorsDates.copy;


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

import main.com.zc.service.academic_advising_instructor.aa_instructor;
import main.com.zc.service.academic_advising_student_profile.aa_student_profile;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="aa_instructor_date_students.getAll",
		     query="SELECT c FROM aa_instructor_date c "
		     )
	,
	@NamedQuery(name="aa_instructor_date.getById",
	query = "from aa_instructor_date d where d.id = :id "
			)
	,
	@NamedQuery(name="aa_instructor_date.getByInstructorIdAndYearAndSemester",
	query = "from aa_instructor_date d where d.instructor.id = :id and d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	,
	@NamedQuery(name="aa_instructor_date.getAllByYearAndSemester",
	query = "from aa_instructor_date d where d.year = :year and Lower(d.semester) = Lower(:semester)"
			)
	
	
	
})
 
@Entity
@Table(name = "taa_instructor_date")
public class aa_instructor_date {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "student")
	private aa_student_profile student;
	
	
	@ManyToOne
	@JoinColumn(name = "instructor")
	private aa_instructor instructor;
	
	
	@Column(name = "year")
	private String year;
	

	@Column(name = "semester")
	private String semester;

	

	@Column(name = "date")
	private Calendar date;



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public aa_student_profile getStudent() {
		return student;
	}



	public void setStudent(aa_student_profile student) {
		this.student = student;
	}



	public aa_instructor getInstructor() {
		return instructor;
	}



	public void setInstructor(aa_instructor instructor) {
		this.instructor = instructor;
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



	public Calendar getDate() {
		return date;
	}



	public void setDate(Calendar date) {
		this.date = date;
	}


	
	
}
