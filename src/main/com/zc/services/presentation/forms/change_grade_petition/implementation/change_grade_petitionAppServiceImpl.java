/**
 * 
 */
package main.com.zc.services.presentation.forms.change_grade_petition.implementation;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.presentation.forms.change_grade_petition.Ichange_grade_petitionAppService;
import main.com.zc.services.presentation.forms.change_grade_petition.change_grade_petition;
import main.com.zc.services.presentation.forms.change_grade_petition.change_grade_petitionRepository;

/**
 * @author Dakrory
 *
 */
@Service("change_grade_petitionFacadeImpl")
public class change_grade_petitionAppServiceImpl implements Ichange_grade_petitionAppService{

	@Autowired
	change_grade_petitionRepository cccRepository;
	
	
	@Override
	public List<change_grade_petition> getAll() {
		try{
			List<change_grade_petition> ccc=cccRepository.getAll();
			
			return ccc;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public change_grade_petition addchange_grade_petition(change_grade_petition ccc) {
		try{
			change_grade_petition ccc2=cccRepository.addchange_grade_petition(ccc);
			return ccc2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(change_grade_petition ccc) {
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
	public List<change_grade_petition> getByStudentId(int id) {
		// TODO Auto-generated method stub
		try{
			List<change_grade_petition> so=cccRepository.getByStudentId(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public List<change_grade_petition> getAllForStepAndMajorId(int id,int step) {
		// TODO Auto-generated method stub
		try{
			List<change_grade_petition> so=cccRepository.getAllForStepAndMajorId(id,step);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public List<change_grade_petition> getByMajorId(int id) {
		// TODO Auto-generated method stub
		try{
			List<change_grade_petition> so=cccRepository.getByMajorId(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public change_grade_petition getById(int id) {
		// TODO Auto-generated method stub
		try{
			change_grade_petition so=cccRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public List<change_grade_petition> getAllForStep(int step) {
		// TODO Auto-generated method stub
				try{
					List<change_grade_petition> so=cccRepository.getAllForStep(step);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public List<change_grade_petition> getAllRefused() {
		try{
			List<change_grade_petition> ccc=cccRepository.getAllRefused();
			
			return ccc;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<change_grade_petition> getAllForStepAndInstructorId(int id, int step) {
		// TODO Auto-generated method stub
				try{
					List<change_grade_petition> so=cccRepository.getAllForStepAndInstructorId(id,step);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}
	
}
		
		

	
		
	


