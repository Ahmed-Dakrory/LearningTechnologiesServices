/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

/**
 * @author omnya
 *
 */
public interface IReportsServiceAcademic {

	public void generateExcelByList(List<CoursePetitionDTO> lst);
	
	
}
