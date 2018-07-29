/**
 * 
 */
package main.com.zc.services.presentation.configuration.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import main.com.zc.services.domain.shared.enumurations.FormsStatusEnum;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class FormsStatusDTO {


	private Integer id;

	private String formTypeName;

	private FormsStatusEnum status;

	private List<Integer> levels=new ArrayList<Integer>();
	private Integer year;
	private BaseDTO semester=new BaseDTO();
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFormTypeName() {
		return formTypeName;
	}

	public void setFormTypeName(String formTypeName) {
		this.formTypeName = formTypeName;
	}

	public FormsStatusEnum getStatus() {
		return status;
	}

	public void setStatus(FormsStatusEnum status) {
		this.status = status;
	}

	public List<Integer> getLevels() {
		return levels;
	}

	public void setLevels(List<Integer> levels) {
		this.levels = levels;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BaseDTO getSemester() {
		return semester;
	}

	public void setSemester(BaseDTO semester) {
		this.semester = semester;
	}

	
	
}
