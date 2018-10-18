/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.clo;





import java.util.List;

import main.com.zc.services.domain.courses.model.clo.CLO;
import main.com.zc.services.domain.courses.model.clo.CLORepository;
import main.com.zc.services.domain.courses.model.clo.ICLOAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("CLOFacadeImpl")
public class CLOAppServiceImpl implements ICLOAppService{

	@Autowired
	CLORepository cloRepository;
	
	
	@Override
	public List<CLO> getAll() {
		try{
			List<CLO> clo=cloRepository.getAll();
			
			return clo;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CLO> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<CLO> clo=cloRepository.getByCourseId(id);
					
					return clo;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public CLO addCLO(CLO clo) {
		try{
			CLO clo2=cloRepository.addCLO(clo);
			return clo2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public boolean delete(CLO data) {
		// TODO Auto-generated method stub
		try{
			cloRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public CLO getById(int id) {
		// TODO Auto-generated method stub
		try{
			CLO objData=cloRepository.getById(id);
			
			return objData;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


