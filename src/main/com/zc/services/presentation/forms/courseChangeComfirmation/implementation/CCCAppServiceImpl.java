/**
 * 
 */
package main.com.zc.services.presentation.forms.courseChangeComfirmation.implementation;





import java.util.List;
import main.com.zc.services.presentation.forms.courseChangeComfirmation.CCC;
import main.com.zc.services.presentation.forms.courseChangeComfirmation.CCCRepository;
import main.com.zc.services.presentation.forms.courseChangeComfirmation.ICCCAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("CCCFacadeImpl")
public class CCCAppServiceImpl implements ICCCAppService{

	@Autowired
	CCCRepository cccRepository;
	
	
	@Override
	public List<CCC> getAll() {
		try{
			List<CCC> ccc=cccRepository.getAll();
			
			return ccc;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public CCC addCCC(CCC ccc) {
		try{
			CCC ccc2=cccRepository.addCCC(ccc);
			return ccc2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(CCC ccc) {
		// TODO Auto-generated method stub
		try{
			cccRepository.delete(ccc);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public List<CCC> getByStudentId(int id) {
		// TODO Auto-generated method stub
		try{
			List<CCC> so=cccRepository.getByStudentId(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public List<CCC> getAllForStepAndMajorId(int id,int step) {
		// TODO Auto-generated method stub
		try{
			List<CCC> so=cccRepository.getAllForStepAndMajorId(id,step);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public List<CCC> getByMajorId(int id) {
		// TODO Auto-generated method stub
		try{
			List<CCC> so=cccRepository.getByMajorId(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public CCC getById(int id) {
		// TODO Auto-generated method stub
		try{
			CCC so=cccRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CCC> getAllForStepAndType(int type, int step) {
		// TODO Auto-generated method stub
				try{
					List<CCC> so=cccRepository.getAllForStepAndType(type, step);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public List<CCC> getAllForStep(int step) {
		// TODO Auto-generated method stub
				try{
					List<CCC> so=cccRepository.getAllForStep(step);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}
	
}
		
		

	
		
	


