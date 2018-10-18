/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.relatedTopics;

import java.util.List;
import main.com.zc.services.domain.courses.model.relatedTopics.RelatedTopics;
import main.com.zc.services.domain.courses.model.relatedTopics.RelatedTopicsRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author omnya
 *
 */
@Repository
@Transactional
public class RelatedTopicsRepositoryImpl implements RelatedTopicsRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<RelatedTopics> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("RelatedTopics.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<RelatedTopics> results=query.list();
		   return results;
	}

	@Override
	public RelatedTopics addRelatedTopics(RelatedTopics relatedTopics) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(relatedTopics);
			tx1.commit();
			session.close(); 
			return relatedTopics; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<RelatedTopics> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("RelatedTopics.getAll");

				 @SuppressWarnings("unchecked")
				List<RelatedTopics> results=query.list();
				   return results;
	}

	@Override
	public boolean delete(RelatedTopics note) {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(note);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public RelatedTopics getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("RelatedTopics.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<RelatedTopics> results=query.list();
		   return results.get(0);
	}


}
