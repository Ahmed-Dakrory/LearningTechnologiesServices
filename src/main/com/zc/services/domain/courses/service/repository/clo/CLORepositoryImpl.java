/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.clo;

import java.util.List;

import main.com.zc.services.domain.courses.model.clo.CLO;
import main.com.zc.services.domain.courses.model.clo.CLORepository;
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
public class CLORepositoryImpl implements CLORepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<CLO> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CLO.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CLO> results=query.list();
		   return results;
	}

	@Override
	public CLO addCLO(CLO clo) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(clo);
			tx1.commit();
			session.close(); 
			return clo; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CLO> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CLO.getAll");

				 @SuppressWarnings("unchecked")
				List<CLO> results=query.list();
				   return results;
	}

	

}
