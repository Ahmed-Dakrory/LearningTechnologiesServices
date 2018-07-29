/**
 * 
 */
package main.com.zc.services.domain.person.service.specification;

import java.util.List;

import main.com.zc.services.domain.person.model.ILoginRepository;
import main.com.zc.services.domain.person.model.Login;
import main.com.zc.services.domain.person.service.exception.AlreadyRegisteredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class IsRegisteredSpecification implements IIsRegisteredSpecification{

	@Autowired
	ILoginRepository loginRep;
	@Override
	public boolean isSatisfied(int fileNo) {
		List<Login> loginRows=loginRep.getAll();
		for(int i=0;i<loginRows.size();i++)
		{
			if(fileNo==loginRows.get(i).getPerson().getFileNo())
				throw new AlreadyRegisteredException("Already Registered Before");
		}
		return true;
	}

}
