/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class WeekFeedbackResultsDTO {

	private Integer questionID;
	private String questionName;
	private List<BaseDTO> selectionsStatistics=new ArrayList<BaseDTO>();
	public Integer getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public List<BaseDTO> getSelectionsStatistics() {
		return selectionsStatistics;
	}
	public void setSelectionsStatistics(List<BaseDTO> selectionsStatistics) {
		this.selectionsStatistics = selectionsStatistics;
	}
	
	
}
