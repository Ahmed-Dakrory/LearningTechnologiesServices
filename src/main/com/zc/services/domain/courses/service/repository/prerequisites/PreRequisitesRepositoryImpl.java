/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.prerequisites;

import java.util.List;
import main.com.zc.services.domain.courses.model.prerequisites.PreRequisites;
import main.com.zc.services.domain.courses.model.prerequisites.PreRequisitesRepository;
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
public class PreRequisitesRepositoryImpl implements PreRequisitesRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<PreRequisites> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("PreRequisites.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<PreRequisites> results=query.list();
		   return results;
	}

	

	@Override
	public List<PreRequisites> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("PreRequisites.getAll");

				 @SuppressWarnings("unchecked")
				List<PreRequisites> results=query.list();
				   return results;
	}

	@Override
	public PreRequisites addPreRequisites(PreRequisites preRequisite) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(preRequisite);
			tx1.commit();
			session.close(); 
			return preRequisite; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(PreRequisites data) {
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
	public PreRequisites getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("PreRequisites.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<PreRequisites> results=query.list();
		   return results.get(0);
	}

}
