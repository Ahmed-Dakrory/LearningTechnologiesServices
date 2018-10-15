/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.SO;





import java.util.List;

import main.com.zc.services.domain.courses.model.SO.ISOAppService;
import main.com.zc.services.domain.courses.model.SO.SO;
import main.com.zc.services.domain.courses.model.SO.SORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("SOFacadeImpl")
public class SOAppServiceImpl implements ISOAppService{

	@Autowired
	SORepository soRepository;
	
	
	@Override
	public List<SO> getAll() {
		try{
			List<SO> so=soRepository.getAll();
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SO> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<SO> so=soRepository.getByCourseId(id);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public SO addSO(SO so) {
		try{
			SO so2=soRepository.addSO(so);
			return so2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	
	
}
		
		

	
		
	


