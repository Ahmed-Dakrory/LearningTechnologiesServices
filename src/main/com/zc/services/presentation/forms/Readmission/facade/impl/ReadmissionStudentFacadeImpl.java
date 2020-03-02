/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.readmission.services.IStudentReadmissionService;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.Readmission.facade.IReadmissionStudentFacade;

/**
 * @author omnya.alaa
 *
 */
@Service("IReadmissionStudentFacade")
public class ReadmissionStudentFacadeImpl implements IReadmissionStudentFacade{

	@Autowired
	IStudentReadmissionService appService;

	@Override
	public ReadmissionDTO add(ReadmissionDTO dto) {
		
		return appService.add(dto);
	}

	@Override
	public List<ReadmissionDTO> getPendingPetitionsOfstuent(Integer studentID) {
		
		return appService.getPendingPetitionsOfstuent(studentID);
	}

	@Override
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent(Integer studentID) {
		
		return appService.getArchievedPetitionsOfstuent(studentID);
	}



	@Override
	public ReadmissionDTO getByID(Integer id) {
		
		return appService.getByID(id);
	}
}
