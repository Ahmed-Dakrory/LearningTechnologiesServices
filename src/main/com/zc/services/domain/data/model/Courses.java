/**
 * 
 */
package main.com.zc.services.domain.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Omnya Alaa
 * 
 */
@NamedQueries({

		@NamedQuery(name = "Courses.getAll", query = "SELECT c FROM Courses c ORDER BY c.name ASC"),
		@NamedQuery(name = "Courses.getById", query = "from Courses c where c.id = :id"),
		@NamedQuery(name = "Courses.getByName", query = "from Courses c where c.name = :name"),
		@NamedQuery(name = "Courses.getByYearAndSemester", query = "from Courses c where c.semester = :semester and c.year = :year ORDER BY c.name ASC"),
		@NamedQuery(name = "Courses.getByCourseCoordinatorID", query = "from Courses c where c.courseCoordinator.mail = :mail"),
		@NamedQuery(name = "Courses.getByNameAndYearAndSemester", query = "from Courses c where c.name = :name and c.year = :year and semester = :semester")})
@Entity
@Table(name = "courses")
public class Courses {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "NAME")
	private String name;

	/**
	 * @author A7med Al-Dakrorys
	 * 
	 */
	@Column(name = "Program")
	private String Program;
	
	@Column(name = "CourseTitle")
	private String CourseTitle;
	
	@Column(name = "Description")
	private String Description;
	
	@Column(name = "Credit")
	private String Credit;
	
	@Column(name = "RequiredElective")
	private Integer  RequiredElective;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	  private List<Courses_Instructors> courseInstructor=new ArrayList<Courses_Instructors>();
		
		@ManyToOne
		@JoinColumn(name = "COORDINATOR_ID")
		private Employee courseCoordinator;
		@Column(name = "SEMESTER")
		private SemesterEnum semester;
		
		@Column(name = "YEAR")
		private Integer  year;
		
		@Column(name = "COURSE_EVAL_HIDE")
		private Boolean hideCourseEval;
	  
		
		
	public Courses(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Courses(String name) {
		super();
		this.name = name;
	}


	
	public Courses() {
		super();
	}

	public Courses(String name, List<Courses_Instructors> courseInstructor) {
		super();
		this.name = name;
		this.courseInstructor = courseInstructor;
	}

	public Courses(Integer id, String name,
			List<Courses_Instructors> courseInstructor) {
		super();
		this.id = id;
		this.name = name;
		this.courseInstructor = courseInstructor;
	}

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

	public List<Courses_Instructors> getCourseInstructor() {
		return courseInstructor;
	}

	public void setCourseInstructor(List<Courses_Instructors> courseInstructor) {
		this.courseInstructor = courseInstructor;
	}

	public Employee getCourseCoordinator() {
		return courseCoordinator;
	}

	public void setCourseCoordinator(Employee courseCoordinator) {
		this.courseCoordinator = courseCoordinator;
	}

	public SemesterEnum getSemester() {
		return semester;
	}

	public void setSemester(SemesterEnum semester) {
		this.semester = semester;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getHideCourseEval() {
		return hideCourseEval;
	}

	public void setHideCourseEval(Boolean hideCourseEval) {
		this.hideCourseEval = hideCourseEval;
	}

	public String getProgram() {
		return Program;
	}

	public void setProgram(String program) {
		Program = program;
	}

	public String getCourseTitle() {
		return CourseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		CourseTitle = courseTitle;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getCredit() {
		return Credit;
	}

	public void setCredit(String credit) {
		Credit = credit;
	}

	public Integer getRequiredElective() {
		return RequiredElective;
	}

	public void setRequiredElective(Integer requiredElective) {
		RequiredElective = requiredElective;
	}

	

}
