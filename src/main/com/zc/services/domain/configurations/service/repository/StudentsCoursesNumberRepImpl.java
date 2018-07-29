/**
 * 
 */
package main.com.zc.services.domain.configurations.service.repository;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import main.com.zc.services.domain.configurations.model.IStudentsCoursesNumberRep;
import main.com.zc.services.domain.configurations.model.StudentsCoursesNumber;

/**
 * @author omnya
 *
 */
@Repository("IStudentsCoursesNumberRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentsCoursesNumberRepImpl implements IStudentsCoursesNumberRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	
	@Override
	public List<StudentsCoursesNumber> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("StudentsCoursesNumber.getAll");

			@SuppressWarnings("unchecked")
			List<StudentsCoursesNumber> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting forms ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public StudentsCoursesNumber getByCourseID(Integer id) {
		try {
			Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("StudentsCoursesNumber.getByCourseID").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<StudentsCoursesNumber> results = query.list();
		return results.get(0);
		}	
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public StudentsCoursesNumber getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("StudentsCoursesNumber.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<StudentsCoursesNumber> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public StudentsCoursesNumber add(StudentsCoursesNumber courseStudent) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
			try{
				   tx = sess.beginTransaction();
					     sess.save(courseStudent);
					     tx.commit();
					     return courseStudent;
				}
			catch (Exception e) {
			     if (tx!=null) tx.rollback();
			    e.printStackTrace();
			    return null;
			 }
			 finally {
			     sess.close();
			     
			 }}



	@Override
	public StudentsCoursesNumber update(StudentsCoursesNumber courseStudent) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     sess.update(courseStudent);
		     tx.commit();
		     return courseStudent;
		 }
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		    e.printStackTrace();
		 return null;
		 }
		 finally {
		     sess.close();
		 }
	}

	@Override
	public boolean remove(StudentsCoursesNumber obj) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(obj);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		 finally {
			 session.close();
		     
		 }
	}

}
