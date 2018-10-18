/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.references;

import java.util.List;import main.com.zc.services.domain.courses.model.references.References;
import main.com.zc.services.domain.courses.model.references.ReferencesRepository;
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
public class ReferencesRepositoryImpl implements ReferencesRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<References> getByCourseId(int id) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("References.getByCourseId").setInteger("id",id);

		@SuppressWarnings("unchecked")
		List<References> results=query.list();
		return results;
	}

	@Override
	public References addReference(References reference) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(reference);
			tx1.commit();
			session.close(); 
			return reference; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<References> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("References.getAll");

				 @SuppressWarnings("unchecked")
				List<References> results=query.list();
				   return results;
	}

	@Override
	public boolean delete(References note) {
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
	public References getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("References.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<References> results=query.list();
		   return results.get(0);
	}

}
