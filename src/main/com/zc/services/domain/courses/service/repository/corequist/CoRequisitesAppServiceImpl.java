/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.corequist;
import java.util.List;

import main.com.zc.services.domain.courses.model.corequisit.CoRequisites;
import main.com.zc.services.domain.courses.model.corequisit.CoRequisitesRepository;
import main.com.zc.services.domain.courses.model.corequisit.ICoRequisitesAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("coRequisitFacadeImpl")
public class CoRequisitesAppServiceImpl implements ICoRequisitesAppService{

	@Autowired
	CoRequisitesRepository coRequisitesRepository;
	
	
	@Override
	public List<CoRequisites> getAllCoRequisites() {
		try{
			List<CoRequisites> coRequisites=coRequisitesRepository.getAll();
			
			return coRequisites;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CoRequisites> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<CoRequisites> coRequisites=coRequisitesRepository.getByCourseId(id);
					
					return coRequisites;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public CoRequisites addCoRequisite(CoRequisites coRequisites) {
		try{
			CoRequisites coRequisites2=coRequisitesRepository.addCoRequisite(coRequisites);

			return coRequisites2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


