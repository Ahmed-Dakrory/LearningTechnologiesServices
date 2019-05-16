/**
 * 
 */
package main.com.zc.services.presentation.forms.courseReplacement.implementation;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.presentation.forms.courseReplacement.IcourseReplacementAppService;
import main.com.zc.services.presentation.forms.courseReplacement.courseReplacement;
import main.com.zc.services.presentation.forms.courseReplacement.courseReplacementRepository;

/**
 * @author Dakrory
 *
 */
@Service("courseReplacementFacadeImpl")
public class courseReplacementAppServiceImpl implements IcourseReplacementAppService{

	@Autowired
	courseReplacementRepository cccRepository;
	
	
	@Override
	public List<courseReplacement> getAll() {
		try{
			List<courseReplacement> ccc=cccRepository.getAll();
			
			return ccc;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public courseReplacement addcourseReplacement(courseReplacement ccc) {
		try{
			courseReplacement ccc2=cccRepository.addcourseReplacement(ccc);
			return ccc2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(courseReplacement ccc) {
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
	public List<courseReplacement> getByStudentId(int id) {
		// TODO Auto-generated method stub
		try{
			List<courseReplacement> so=cccRepository.getByStudentId(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public List<courseReplacement> getAllForStepAndMajorId(int id,int step) {
		// TODO Auto-generated method stub
		try{
			List<courseReplacement> so=cccRepository.getAllForStepAndMajorId(id,step);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public List<courseReplacement> getByMajorId(int id) {
		// TODO Auto-generated method stub
		try{
			List<courseReplacement> so=cccRepository.getByMajorId(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public courseReplacement getById(int id) {
		// TODO Auto-generated method stub
		try{
			courseReplacement so=cccRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<courseReplacement> getAllForStepAndType(int type, int step) {
		// TODO Auto-generated method stub
				try{
					List<courseReplacement> so=cccRepository.getAllForStepAndType(type, step);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public List<courseReplacement> getAllForStep(int step) {
		// TODO Auto-generated method stub
				try{
					List<courseReplacement> so=cccRepository.getAllForStep(step);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}
	
}
		
		

	
		
	


