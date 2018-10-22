/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.topics;





import java.util.List;
import main.com.zc.services.domain.courses.model.topics.ITopicsAppService;
import main.com.zc.services.domain.courses.model.topics.TopicRepository;
import main.com.zc.services.domain.courses.model.topics.Topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("TopicFacadeImpl")
public class TopicAppServiceImpl implements ITopicsAppService{

	@Autowired
	TopicRepository topicRepository;
	
	
	@Override
	public List<Topics> getAll() {
		try{
			List<Topics> topics=topicRepository.getAll();
			
			return topics;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Topics> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<Topics> topics=topicRepository.getByCourseId(id);
					
					return topics;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public Topics addTopic(Topics topic) {
		try{
			Topics topics=topicRepository.addTopic(topic);
			return topics;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public boolean delete(Topics data) {
		// TODO Auto-generated method stub
		try{
			topicRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public Topics getById(int id) {
		// TODO Auto-generated method stub
		try{
			Topics objData=topicRepository.getById(id);
			
			return objData;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


