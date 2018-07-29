/**
 * 
 */
package main.com.zc.services.domain.person.service.business;

import main.com.zc.services.domain.person.model.ILoginRepository;
import main.com.zc.services.domain.person.model.Login;
import main.com.zc.services.domain.person.service.exception.AlreadyRegisteredException;
import main.com.zc.services.domain.person.service.specification.IIsRegisteredSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya ALaa
 *
 */
@Service
public class AddLoginDomainServiceImpl implements IAddLoginDomainService{

	@Autowired
	ILoginRepository loginRep;
	@Autowired
	IIsRegisteredSpecification registeredSpec;
	
	// Will Return 0 if already Registered and will return his inserted row id in Login table if not
	@Override
	public int addLogin(Login login) {
		try
		{
			if(registeredSpec.isSatisfied(login.getPerson().getFileNo()))// true if not registered
		
			return loginRep.add(login);
			else 
				return 0;
		}
		 catch(AlreadyRegisteredException ex)
		 {
			 ex.printStackTrace();
		return 0;
		 }
		
	}

}
