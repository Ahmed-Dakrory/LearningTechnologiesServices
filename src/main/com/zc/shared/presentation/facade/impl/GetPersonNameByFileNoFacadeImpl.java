/**
 * 
 */
package main.com.zc.shared.presentation.facade.impl;

import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.shared.presentation.facade.IGetPersonNameByFileNoFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class GetPersonNameByFileNoFacadeImpl implements IGetPersonNameByFileNoFacade {

	@Autowired
	IStudentRepository personRep;
	@Override
	public String getNameByFileNo(int fileNo) {
		// TODO Auto-generated method stub
		
		try
		{
			return personRep.getPersonByFileNo(fileNo).getData().getNameInArabic();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "";
		}
		
	}

}
