/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.courseTa;

import java.util.List;

import main.com.zc.services.domain.courses.model.courseTa.CourseTa;
import main.com.zc.services.domain.courses.model.courseTa.CourseTaRepository;

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
public class CourseTaRepositoryImpl implements CourseTaRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<CourseTa> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CourseTa.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CourseTa> results=query.list();
		   return results;
	}

	@Override
	public CourseTa addCourseTa(CourseTa courseTa) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(courseTa);
			tx1.commit();
			session.close(); 
			return courseTa; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CourseTa> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CourseTa.getAll");

				 @SuppressWarnings("unchecked")
				List<CourseTa> results=query.list();
				   return results;
	}

	

}
