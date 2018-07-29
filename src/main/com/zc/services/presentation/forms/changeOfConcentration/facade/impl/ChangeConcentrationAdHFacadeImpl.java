/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IChangeConcentrationAdHService;
import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IChangeConcentrationRegistrarService;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationAdHFacade;

/**
 * @author omnya
 *
 */
@Service("IChangeConcentrationAdHFacade")
public class ChangeConcentrationAdHFacadeImpl implements IChangeConcentrationAdHFacade{

	@Autowired
	IChangeConcentrationAdHService service;
	@Autowired
	IChangeConcentrationRegistrarService registrarService;
	@Override
	public List<ChangeConcentrationDTO> getPendingFormsOfAdmissionHead() {
		return service.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<ChangeConcentrationDTO> getArchievedFormsOfAdmissionHead() {
		
		return service.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto) {
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<ChangeConcentrationDTO> getOldSummer(Integer year) {
		return registrarService.getOldFall(year);
	}

	@Override
	public List<ChangeConcentrationDTO> getOldSpring(Integer year) {
		return registrarService.getOldSpring(year);
	}

	@Override
	public List<ChangeConcentrationDTO> getOldFall(Integer year) {
		return registrarService.getOldFall(year);
	}



}
