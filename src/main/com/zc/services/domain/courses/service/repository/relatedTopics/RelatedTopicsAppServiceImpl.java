/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.relatedTopics;
import java.util.List;
import main.com.zc.services.domain.courses.model.relatedTopics.IRelatedTopicsAppService;
import main.com.zc.services.domain.courses.model.relatedTopics.RelatedTopics;
import main.com.zc.services.domain.courses.model.relatedTopics.RelatedTopicsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("relatedTopicsFacadeImpl")
public class RelatedTopicsAppServiceImpl implements IRelatedTopicsAppService{

	@Autowired
	RelatedTopicsRepository relatedTopicsRepository;
	
	
	@Override
	public List<RelatedTopics> getAllRelatedTopics() {
		try{
			List<RelatedTopics> relatedTopics=relatedTopicsRepository.getAll();
			
			return relatedTopics;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<RelatedTopics> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<RelatedTopics> relatedTopics=relatedTopicsRepository.getByCourseId(id);
					
					return relatedTopics;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public RelatedTopics addRelatedTopics(RelatedTopics relatedTopics) {
		try{
			RelatedTopics relatedTopics2=relatedTopicsRepository.addRelatedTopics(relatedTopics);
			return relatedTopics2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


