/**
 * 
 */
package main.com.zc.services.presentation.users.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.appService.IPersonGetDataAppService;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@Service("GetLoggedInStudentDataFacadeImpl")
public class GetLoggedInStudentDataFacadeImpl implements IGetLoggedInStudentDataFacade{

	@Autowired
	IPersonGetDataAppService personAPPService;
	
	@Override
	public PersonDataDTO getPersonByPersonMail(String mail) {
	
		return personAPPService.getPersonByPersonMail(mail);
	}

	@Override
	public PersonDataDTO getPersonByFileNo(Integer id) {
		return personAPPService.getPersonByFileNo(id);
	}

}
