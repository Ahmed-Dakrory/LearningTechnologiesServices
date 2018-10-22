/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.topics;

import java.util.List;
import main.com.zc.services.domain.courses.model.topics.TopicRepository;
import main.com.zc.services.domain.courses.model.topics.Topics;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author A7med Al-Dakrory
 *
 */
@Repository
@Transactional
public class TopicRepositoryImpl implements TopicRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<Topics> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Topics.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<Topics> results=query.list();
		   return results;
	}

	@Override
	public Topics addTopic(Topics topics) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(topics);
			tx1.commit();
			session.close(); 
			return topics; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Topics> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Topics.getAll");

				 @SuppressWarnings("unchecked")
				List<Topics> results=query.list();
				   return results;
	}

	


	@Override
	public boolean delete(Topics data) {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(data);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Topics getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Topics.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<Topics> results=query.list();
		   return results.get(0);
	}
	
}
