/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.dto;

import main.com.zc.services.domain.shared.enumurations.ScaleSelectionTypeEnum;

/**
 * @author Omnya Alaa
 *
 */
public class ScaleSelectionsDTO {


	private Integer id;

	
	private String name;
	
	

	private ScaleSelectionTypeEnum type;
	

	private ScaleTypeDTO scaleType;


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


	public ScaleSelectionTypeEnum getType() {
		return type;
	}


	public void setType(ScaleSelectionTypeEnum type) {
		this.type = type;
	}


	public ScaleTypeDTO getScaleType() {
		return scaleType;
	}


	public void setScaleType(ScaleTypeDTO scaleType) {
		this.scaleType = scaleType;
	}
	
	
	
	
}
