/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Omnya Alaa
 *
 */
public class ScaleTypeDTO {


	private Integer id;
	
	
	private String name;
  
	private List<ScaleSelectionsDTO> selections =new ArrayList<ScaleSelectionsDTO>();

	
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


	public List<ScaleSelectionsDTO> getSelections() {
		return selections;
	}


	public void setSelections(List<ScaleSelectionsDTO> selections) {
		this.selections = selections;
	}
	
	
}
