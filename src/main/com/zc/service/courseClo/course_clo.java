package main.com.zc.service.courseClo;


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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="course_clo.getAll",
		     query="SELECT c FROM course_clo c "
		     )
	,
	@NamedQuery(name="course_clo.getById",
	query = "from course_clo d where d.id = :id "
			)
	,
	@NamedQuery(name="course_clo.getAllByYearAndSemestar",
	query = "from course_clo d where d.year = :year and d.semester = :semestar"
			)
	,
	@NamedQuery(name="course_clo.getAllByYearAndSemestarAndCourseCode",
	query = "from course_clo d where d.year = :year and d.semester = :semestar and d.course_code = :course_code"
			)
		
	
})

@Entity
@Table(name = "courses")
public class course_clo {
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "Program")
	private String Program;
	
	@Column(name = "CourseTitle")
	private String CourseTitle;
	
	@Column(name = "Description")
	private String Description;

	@Column(name = "Credit")
	private String Credit;
	

	@Column(name = "clo")
	private String clo;
	
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
	  
	
	@Column(name = "NAME")
	private String course_code;
	

	@Column(name = "clo1")
	private String clo1;
	

	@Column(name = "clo2")
	private String clo2;

	

	@Column(name = "clo3")
	private String clo3;

	

	@Column(name = "clo4")
	private String clo4;

	

	@Column(name = "clo5")
	private String clo5;

	

	@Column(name = "clo6")
	private String clo6;

	

	@Column(name = "clo7")
	private String clo7;

	
	

	@Column(name = "clo8")
	private String clo8;

	

	@Column(name = "clo9")
	private String clo9;

	@Column(name = "clo10")
	private String clo10;
	

	@Column(name = "clo11")
	private String clo11;
	

	@Column(name = "clo12")
	private String clo12;

	

	@Column(name = "clo13")
	private String clo13;

	

	@Column(name = "clo14")
	private String clo14;

	

	@Column(name = "clo15")
	private String clo15;

	

	@Column(name = "clo16")
	private String clo16;

	

	@Column(name = "clo17")
	private String clo17;

	
	

	@Column(name = "clo18")
	private String clo18;

	

	@Column(name = "clo19")
	private String clo19;

	@Column(name = "clo20")
	private String clo20;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getClo() {
		return clo;
	}

	public void setClo(String clo) {
		this.clo = clo;
	}

	public Integer getRequiredElective() {
		return RequiredElective;
	}

	public void setRequiredElective(Integer requiredElective) {
		RequiredElective = requiredElective;
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

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
	}

	public String getClo1() {
		return clo1;
	}

	public void setClo1(String clo1) {
		this.clo1 = clo1;
	}

	public String getClo2() {
		return clo2;
	}

	public void setClo2(String clo2) {
		this.clo2 = clo2;
	}

	public String getClo3() {
		return clo3;
	}

	public void setClo3(String clo3) {
		this.clo3 = clo3;
	}

	public String getClo4() {
		return clo4;
	}

	public void setClo4(String clo4) {
		this.clo4 = clo4;
	}

	public String getClo5() {
		return clo5;
	}

	public void setClo5(String clo5) {
		this.clo5 = clo5;
	}

	public String getClo6() {
		return clo6;
	}

	public void setClo6(String clo6) {
		this.clo6 = clo6;
	}

	public String getClo7() {
		return clo7;
	}

	public void setClo7(String clo7) {
		this.clo7 = clo7;
	}

	public String getClo8() {
		return clo8;
	}

	public void setClo8(String clo8) {
		this.clo8 = clo8;
	}

	public String getClo9() {
		return clo9;
	}

	public void setClo9(String clo9) {
		this.clo9 = clo9;
	}

	public String getClo10() {
		return clo10;
	}

	public void setClo10(String clo10) {
		this.clo10 = clo10;
	}

	public String getClo11() {
		return clo11;
	}

	public void setClo11(String clo11) {
		this.clo11 = clo11;
	}

	public String getClo12() {
		return clo12;
	}

	public void setClo12(String clo12) {
		this.clo12 = clo12;
	}

	public String getClo13() {
		return clo13;
	}

	public void setClo13(String clo13) {
		this.clo13 = clo13;
	}

	public String getClo14() {
		return clo14;
	}

	public void setClo14(String clo14) {
		this.clo14 = clo14;
	}

	public String getClo15() {
		return clo15;
	}

	public void setClo15(String clo15) {
		this.clo15 = clo15;
	}

	public String getClo16() {
		return clo16;
	}

	public void setClo16(String clo16) {
		this.clo16 = clo16;
	}

	public String getClo17() {
		return clo17;
	}

	public void setClo17(String clo17) {
		this.clo17 = clo17;
	}

	public String getClo18() {
		return clo18;
	}

	public void setClo18(String clo18) {
		this.clo18 = clo18;
	}

	public String getClo19() {
		return clo19;
	}

	public void setClo19(String clo19) {
		this.clo19 = clo19;
	}

	public String getClo20() {
		return clo20;
	}

	public void setClo20(String clo20) {
		this.clo20 = clo20;
	}
	

	




	
	
}
