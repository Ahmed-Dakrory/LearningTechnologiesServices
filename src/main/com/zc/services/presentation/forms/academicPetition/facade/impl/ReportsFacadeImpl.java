/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.academicPetition.services.IReportsServiceAcademic;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("IReportsFacadeAcademicPet")
public class ReportsFacadeImpl implements IReportsFacade{

	@Autowired
	IReportsServiceAcademic service;
	
	
	@Override
	public void generateExcelByList(List<CoursePetitionDTO> lst) {
		service.generateExcelByList(lst);
		
	}

}
