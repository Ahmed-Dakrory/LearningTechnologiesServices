/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.SO;

import java.util.List;

import main.com.zc.services.domain.courses.model.SO.SO;
import main.com.zc.services.domain.courses.model.SO.SORepository;
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
public class SORepositoryImpl implements SORepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<SO> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SO.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<SO> results=query.list();
		   return results;
	}

	@Override
	public SO addSO(SO so) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(so);
			tx1.commit();
			session.close(); 
			return so; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SO> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SO.getAll");

				 @SuppressWarnings("unchecked")
				List<SO> results=query.list();
				   return results;
	}

	
	@Override
	public boolean delete(SO note) {
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
	public SO getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("SO.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<SO> results=query.list();
		   return results.get(0);
	}

}
