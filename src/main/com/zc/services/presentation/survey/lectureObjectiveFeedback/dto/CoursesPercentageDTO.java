/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class CoursesPercentageDTO extends CoursesDTO{

	private Double persentage;

	public Double getPersentage() {
		return persentage;
	}

	public void setPersentage(Double persentage) {
		this.persentage = persentage;
	}
	
}
