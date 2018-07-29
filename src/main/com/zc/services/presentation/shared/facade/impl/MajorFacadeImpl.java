/**
 * 
 */
package main.com.zc.services.presentation.shared.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.IMajorAppService;
import main.com.zc.services.presentation.shared.facade.IMajorFacade;

/**
 * @author Omnya Alaa
 *
 */
@Service("IMajorFacade")
public class MajorFacadeImpl implements IMajorFacade{

	@Autowired
	IMajorAppService facade;
	@Override
	public boolean isMajorHead(Integer empID) {
		
		return facade.isMajorHead(empID);
	}

}
