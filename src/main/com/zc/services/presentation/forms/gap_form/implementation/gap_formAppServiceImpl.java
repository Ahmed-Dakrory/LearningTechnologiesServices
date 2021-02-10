/**
 * 
 */
package main.com.zc.services.presentation.forms.gap_form.implementation;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.presentation.forms.gap_form.Igap_formAppService;
import main.com.zc.services.presentation.forms.gap_form.gap_form;
import main.com.zc.services.presentation.forms.gap_form.gap_formRepository;

/**
 * @author Dakrory
 *
 */
@Service("gap_formFacadeImpl")
public class gap_formAppServiceImpl implements Igap_formAppService{

	@Autowired
	gap_formRepository cccRepository;
	
	
	@Override
	public List<gap_form> getAll() {
		try{
			List<gap_form> ccc=cccRepository.getAll();
			
			return ccc;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public gap_form addgap_form(gap_form ccc) {
		try{
			gap_form ccc2=cccRepository.addgap_form(ccc);
			return ccc2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(gap_form ccc) {
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
	public List<gap_form> getByStudentId(int id) {
		// TODO Auto-generated method stub
		try{
			List<gap_form> so=cccRepository.getByStudentId(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public List<gap_form> getAllForStepAndMajorId(int id,int step) {
		// TODO Auto-generated method stub
		try{
			List<gap_form> so=cccRepository.getAllForStepAndMajorId(id,step);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public List<gap_form> getByMajorId(int id) {
		// TODO Auto-generated method stub
		try{
			List<gap_form> so=cccRepository.getByMajorId(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	@Override
	public gap_form getById(int id) {
		// TODO Auto-generated method stub
		try{
			gap_form so=cccRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public List<gap_form> getAllForStep(int step) {
		// TODO Auto-generated method stub
				try{
					List<gap_form> so=cccRepository.getAllForStep(step);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public List<gap_form> getAllRefused() {
		try{
			List<gap_form> ccc=cccRepository.getAllRefused();
			
			return ccc;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


