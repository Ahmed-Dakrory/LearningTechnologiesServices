/**
 * 
 */
package main.com.zc.services.presentation.users.dto;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class MajorDTO {
	private Integer id;
	private String majorName;
    private  InstructorDTO headOfMajor=new InstructorDTO();
    private Integer type;
    private boolean visabiltiy;
    List<BaseDTO> concentrations=new ArrayList<BaseDTO>();
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public InstructorDTO getHeadOfMajor() {
		return headOfMajor;
	}
	public void setHeadOfMajor(InstructorDTO headOfMajor) {
		this.headOfMajor = headOfMajor;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<BaseDTO> getConcentrations() {
		return concentrations;
	}
	public void setConcentrations(List<BaseDTO> concentrations) {
		this.concentrations = concentrations;
	}
	public boolean isVisabiltiy() {
		return visabiltiy;
	}
	public void setVisabiltiy(boolean visabiltiy) {
		this.visabiltiy = visabiltiy;
	}

    
    
}
