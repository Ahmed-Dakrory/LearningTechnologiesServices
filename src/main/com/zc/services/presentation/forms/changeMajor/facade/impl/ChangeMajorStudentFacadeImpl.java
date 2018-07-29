/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeMajor.services.IStudentChangeMajorService;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IChangeMajorStudentFacade;

/**
 * @author omnya.alaa
 *
 */
@Service("IChangeMajorStudentFacade")
public class ChangeMajorStudentFacadeImpl implements IChangeMajorStudentFacade{

	@Autowired
	IStudentChangeMajorService appService;

	@Override
	public ChangeMajorDTO add(ChangeMajorDTO dto) {
		
		return appService.add(dto);
	}

	@Override
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent(Integer studentID) {
		
		return appService.getPendingPetitionsOfstuent(studentID);
	}

	@Override
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent(Integer studentID) {
		
		return appService.getArchievedPetitionsOfstuent(studentID);
	}



	@Override
	public ChangeMajorDTO getByID(Integer id) {
		
		return appService.getByID(id);
	}
}
