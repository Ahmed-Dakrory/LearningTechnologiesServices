/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IChangeConcentrationRegistrarService;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationRegistrarFacade;

/**
 * @author omnya
 *
 */
@Service("IChangeConcentrationRegistrarFacade")
public class ChangeConcentrationRegistrarFacadeImpl implements IChangeConcentrationRegistrarFacade{

	@Autowired
	IChangeConcentrationRegistrarService service;
	@Override
	public ChangeConcentrationDTO updateRequest(ChangeConcentrationDTO dto) {
	
		return service.updateRequest(dto);
	}

	@Override
	public List<ChangeConcentrationDTO> getPendingPetitionsOfstuent() {
	
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<ChangeConcentrationDTO> getArchievedPetitionsOfstuent() {
		
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public List<ChangeConcentrationDTO> getOldSummer(Integer year) {
		
		return service.getOldSummer(year);
	}

	@Override
	public List<ChangeConcentrationDTO> getOldSpring(Integer year) {
		return service.getOldSpring(year);
	}

	@Override
	public List<ChangeConcentrationDTO> getOldFall(Integer year) {
		return service.getOldFall(year);
	}

}
