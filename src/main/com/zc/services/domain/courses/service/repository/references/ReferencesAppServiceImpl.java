/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.references;
import java.util.List;

import main.com.zc.services.domain.courses.model.references.IReferencesAppService;
import main.com.zc.services.domain.courses.model.references.References;
import main.com.zc.services.domain.courses.model.references.ReferencesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("referencesFacadeImpl")
public class ReferencesAppServiceImpl implements IReferencesAppService{

	@Autowired
	ReferencesRepository referencesRepository;
	
	
	@Override
	public List<References> getAllReferences() {
		try{
			List<References> references=referencesRepository.getAll();
			
			return references;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<References> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<References> references=referencesRepository.getByCourseId(id);
					
					return references;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public References addReference(References reference) {
		try{
			References Reference=referencesRepository.addReference(reference);

			return Reference;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


