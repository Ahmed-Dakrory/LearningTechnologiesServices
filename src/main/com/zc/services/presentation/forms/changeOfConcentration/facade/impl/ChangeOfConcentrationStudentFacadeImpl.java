/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IChangeOfConcentrationStudentService;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeOfConcentrationStudentFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service("IChangeOfConcentrationStudentFacade")
public class ChangeOfConcentrationStudentFacadeImpl implements IChangeOfConcentrationStudentFacade{

	@Autowired
	IChangeOfConcentrationStudentService service;
	@Override
	public List<MajorDTO> getAllMajors() {
		
		return service.getAllMajors();
	}
	@Override
	public List<BaseDTO> getConcentrationsByMajor(Integer majorID) {
	
		return service.getConcentrationsByMajor( majorID) ;
	}
	@Override
	public ChangeConcentrationDTO add(ChangeConcentrationDTO request) {
		
		return service.add(request);
	}
	@Override
	public List<ChangeConcentrationDTO> getPendingPetitionsByStudentID(Integer studentID) {
		return service.getPendingPetitionsByStudentID(studentID);
	}
	@Override
	public List<ChangeConcentrationDTO> getArchievedPetitionsByStudentID(Integer studentID) {
		return service.getArchievedPetitionsByStudentID(studentID);
	}


}
