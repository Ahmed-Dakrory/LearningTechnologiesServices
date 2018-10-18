/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.courseIns;

import java.util.List;

import main.com.zc.services.domain.courses.model.courseIns.CourseInSyllabus;
import main.com.zc.services.domain.courses.model.courseIns.CourseInsSyllabusRepository;
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
public class CourseInsRepositoryImpl implements CourseInsSyllabusRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<CourseInSyllabus> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CourseInSyllabus.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CourseInSyllabus> results=query.list();
		   return results;
	}

	@Override
	public CourseInSyllabus addCourseIns(CourseInSyllabus courseIns) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(courseIns);
			tx1.commit();
			session.close(); 
			return courseIns; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CourseInSyllabus> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CourseInSyllabus.getAll");

				 @SuppressWarnings("unchecked")
				List<CourseInSyllabus> results=query.list();
				   return results;
	}

	@Override
	public boolean delete(CourseInSyllabus data) {
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
	public CourseInSyllabus getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CourseInSyllabus.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CourseInSyllabus> results=query.list();
		   return results.get(0);
	}

}
