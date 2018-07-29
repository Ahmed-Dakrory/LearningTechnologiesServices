/**
 * 
 */
package main.com.zc.services.domain.feedback.service.repository;

import java.util.List;

import main.com.zc.services.domain.feedback.model.Feedback;
import main.com.zc.services.domain.feedback.model.FeedbackCategory;
import main.com.zc.services.domain.feedback.model.ICategoryRepository;
import main.com.zc.services.domain.feedback.model.IFeedbackRepository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Omnya Alaa
 *
 */
@Repository
@Transactional
public class CategoryRepositoryImpl implements ICategoryRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	
	@Override
	public List<FeedbackCategory> getAll() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("FeedbackCategory.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<FeedbackCategory> results=query.list();
		   return results;
	}

	@Override
	public FeedbackCategory getByID(int id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("FeedbackCategory.getById").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<FeedbackCategory> results=query.list();
		   return results.get(0);
	}

}
