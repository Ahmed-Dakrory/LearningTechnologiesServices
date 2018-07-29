/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade;

import java.util.List;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

/**
 * @author omnya
 *
 */
public interface IReportsFacade {

	public void generateExcelByList(List<CoursePetitionDTO> lst);
	
}
