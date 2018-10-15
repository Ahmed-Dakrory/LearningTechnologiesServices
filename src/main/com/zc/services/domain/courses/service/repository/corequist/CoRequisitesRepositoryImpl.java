/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.corequist;

import java.util.List;

import main.com.zc.services.domain.courses.model.corequisit.CoRequisites;
import main.com.zc.services.domain.courses.model.corequisit.CoRequisitesRepository;

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
public class CoRequisitesRepositoryImpl implements CoRequisitesRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<CoRequisites> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CoRequisites.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CoRequisites> results=query.list();
		   return results;
	}

	@Override
	public CoRequisites addCoRequisite(CoRequisites coRequisites) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(coRequisites);
			tx1.commit();
			session.close(); 
			return coRequisites; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CoRequisites> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CoRequisites.getAll");

				 @SuppressWarnings("unchecked")
				List<CoRequisites> results=query.list();
				   return results;
	}

}
