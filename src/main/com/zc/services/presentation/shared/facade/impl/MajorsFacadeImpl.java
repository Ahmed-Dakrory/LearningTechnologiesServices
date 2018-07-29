/**
 * 
 */
package main.com.zc.services.presentation.shared.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.IMajorAppService;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.declarationOfConcentration.facade.IDeclarationOfConcentrationFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service("IMajorsFacade")
public class MajorsFacadeImpl implements IMajorsFacade{

	@Autowired
	IMajorAppService service;
	@Autowired
	IDeclarationOfConcentrationFacade facade;
	@Override
	public List<MajorDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public List<MajorDTO> getAvailableOnly() {
		return service.getAvailableOnly();
	}

	@Override
	public List<MajorDTO> getMajorsWithConcentrations() {
		List<MajorDTO>lst= service.getAll();
		List<BaseDTO> concentrations;
		for(MajorDTO major:lst)
		{
			 concentrations=new ArrayList<>();
			 concentrations=facade.getConcentrationsByMajor(major.getId());
			 major.setConcentrations(concentrations);
		}
		return lst;
		
	}

	@Override
	public MajorDTO addMajor(MajorDTO major) {
		// TODO Auto-generated method stub
		return service.addMajor(major);
	}
	@Override
	public MajorDTO updateMajor(MajorDTO major) {
		
		return service.updateMajor(major);
	}
	@Override
	public boolean deleteMajor(MajorDTO major) {
		return service.deleteMajor(major);
	}

	@Override
	public boolean deleteCocnentration(BaseDTO con) {
		return service.deleteCocnentration(con);
	}

	@Override
	public boolean hideCocnentration(BaseDTO con) {
		return service.hideConcentration(con.getId());
	}

	@Override
	public BaseDTO addConcentration(BaseDTO major) {
		return service.addConcentration(major);
	}

}
