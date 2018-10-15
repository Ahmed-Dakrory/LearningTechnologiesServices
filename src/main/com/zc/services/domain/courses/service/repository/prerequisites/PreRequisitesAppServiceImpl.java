/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.prerequisites;
import java.util.List;

import main.com.zc.services.domain.courses.model.prerequisites.IPreRequisitesAppService;
import main.com.zc.services.domain.courses.model.prerequisites.PreRequisites;
import main.com.zc.services.domain.courses.model.prerequisites.PreRequisitesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("preRequisiteFacadeImpl")
public class PreRequisitesAppServiceImpl implements IPreRequisitesAppService{

	@Autowired
	PreRequisitesRepository preRequisitesRepository;
	
	
	@Override
	public List<PreRequisites> getAllPreRequisites() {
		try{
			List<PreRequisites> preRequisites=preRequisitesRepository.getAll();
			
			return preRequisites;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<PreRequisites> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<PreRequisites> preRequisites=preRequisitesRepository.getByCourseId(id);
					
					return preRequisites;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public PreRequisites addPreRequisite(PreRequisites preRequisite) {
		try{
			PreRequisites preRequisite2=preRequisitesRepository.addPreRequisites(preRequisite);

			return preRequisite2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


