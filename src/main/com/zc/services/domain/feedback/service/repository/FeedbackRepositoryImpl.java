/**
 * 
 */
package main.com.zc.services.domain.feedback.service.repository;

import java.util.List;

import main.com.zc.services.domain.feedback.model.Feedback;
import main.com.zc.services.domain.feedback.model.IFeedbackRepository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Omnya Alaa
 *
 */
@Repository("IFeedbackRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FeedbackRepositoryImpl implements IFeedbackRepository {

	
	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	
	@Override
	public Feedback add(Feedback feedback)  {
		try{
			sessionFactory.getCurrentSession().save(feedback);
			return feedback;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean remove(Integer id) {
		try
		{ 
		session = sessionFactory.openSession();
		 Transaction tx1 = session.beginTransaction();
		 session.delete(getById(id));
		    tx1.commit();
			session.close();
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public Feedback update(Feedback feedback) {
		 try{
				session = sessionFactory.openSession();
				Transaction tx1 = session.beginTransaction();
				session.update(feedback);
				tx1.commit();
				session.close();
				return feedback;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Feedback> getAll() {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getAll");

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}



	@Override
	public Feedback getById(Integer id) {
		  Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getById").setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results.get(0);
	}

	@Override
	public List<Feedback> getFeedbackPersonID(Integer personId) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getFeedbackPersonID").setInteger("id", personId);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getByCategId(Integer id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getByCategId").setInteger("id", id);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getByPersonIdAndStep(Integer id, Integer step) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getByPersonIdAndStep").setInteger("id", id).setInteger("step",step);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getByCategoryAndStep(Integer id, Integer step) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getByCategoryAndStep").
				 setInteger("id", id).setInteger("step",step);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getByMajorAndStep(Integer id, Integer step) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getByMajorAndStep").
				 setInteger("id", id).setInteger("step",step);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getByMajorHeadAndStep(Integer id, Integer step) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getByMajorHeadAndStep").
				 setInteger("id", id).setInteger("step",step);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getByStep(Integer step) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getByStep").
				 setInteger("step",step);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getoldByStudentID(Integer id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getoldByStudentID").
				 setInteger("id",id);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getPendingByMajorHead(Integer insID) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback." +
		 		"getPendingByMajorHead").
				 setInteger("id",insID);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getOldByMajorHead(Integer insID) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback." +
		 		"getOldByMajorHead").
				 setInteger("id",insID);

	        
		   @SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getHandlerOld() {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback.getHandlerOld");

		@SuppressWarnings("unchecked")
		List<Feedback> results=query.list();
		   return results;
	}

	@Override
	public List<Feedback> getByCategoryHeadAndStep(Integer id, Integer step) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback." +
			 		"getByCategoryHeadAndStep").
					 setInteger("id", id).setInteger("step",step);

		        
			   @SuppressWarnings("unchecked")
			List<Feedback> results=query.list();
			   return results;
	}

	@Override
	public List<Feedback> getPendingByCategoryHead(Integer id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback." +
			 		"getPendingByCategoryHead").
					 setInteger("id", id);

		        
			   @SuppressWarnings("unchecked")
			List<Feedback> results=query.list();
			   return results;
	}

	@Override
	public List<Feedback> getOldByCategoryHead(Integer id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Feedback." +
			 		"getOldByCategoryHead").
					 setInteger("id", id);

		        
			   @SuppressWarnings("unchecked")
			List<Feedback> results=query.list();
			   return results;
	}

}
