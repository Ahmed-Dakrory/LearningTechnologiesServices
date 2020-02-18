/**
 * 
 */
package main.com.zc.services.presentation.users.dto;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.domain.survey.model.Concentration;

/**
 * @author omnya
 *
 */
public class StudentProfileDTO {


	private Integer  id;

	private MajorDTO major;
	
	private String minor;
	
	private Concentration concentration;
	
	private Double registeredCreditHrs;
	
	private Double completedCreditHrs;
	
	private Integer repeatedCourses;
	
	private SemesterEnum semester;
		
	private Integer  year;

	private StudentDTO student;

	private String gender;
	
	private String mobile;
	
	private byte[] studentImage;
	
	private Double gpa;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MajorDTO getMajor() {
		return major;
	}

	public void setMajor(MajorDTO major) {
		this.major = major;
	}

	public Double getRegisteredCreditHrs() {
		return registeredCreditHrs;
	}

	public void setRegisteredCreditHrs(Double registeredCreditHrs) {
		this.registeredCreditHrs = registeredCreditHrs;
	}

	public Double getCompletedCreditHrs() {
		return completedCreditHrs;
	}

	public void setCompletedCreditHrs(Double completedCreditHrs) {
		this.completedCreditHrs = completedCreditHrs;
	}

	public Integer getRepeatedCourses() {
		return repeatedCourses;
	}

	public void setRepeatedCourses(Integer repeatedCourses) {
		this.repeatedCourses = repeatedCourses;
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

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public byte[] getStudentImage() {
		return studentImage;
	}

	public void setStudentImage(byte[] studentImage) {
		this.studentImage = studentImage;
	}
	
	public Double getGpa() {
		return gpa;
	}
	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	public Concentration getConcentration() {
		return concentration;
	}

	public void setConcentration(Concentration concentration) {
		this.concentration = concentration;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}
	
	
}
