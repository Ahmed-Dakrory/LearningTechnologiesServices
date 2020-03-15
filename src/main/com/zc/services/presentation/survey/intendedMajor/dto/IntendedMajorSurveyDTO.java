/**
 * 
 */
package main.com.zc.services.presentation.survey.intendedMajor.dto;

import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class IntendedMajorSurveyDTO {


	private Integer id;
	
	private String mobile;
	
	private MajorDTO major;
	
	private StudentDTO student;

	private BaseDTO concentration;
	
	private Integer year;
	private Integer semester;
	private Integer state;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public MajorDTO getMajor() {
		return major;
	}

	public void setMajor(MajorDTO major) {
		this.major = major;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public BaseDTO getConcentration() {
		return concentration;
	}

	public void setConcentration(BaseDTO concentration) {
		this.concentration = concentration;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	public String getStateString() {
		if(state == 0) {
			return "Waiting Action...";
		}else if(state == 1) {
			return "Accepted";
		}else if(state == 2) {
			return "Refused";
		}
		return "";
	}
	
}
