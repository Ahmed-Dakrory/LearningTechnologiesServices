/**
 * 
 */
package main.com.zc.services.domain.lectureObjectiveFeedback.service;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.ISemesterWeeksRepository;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.SemesterWeeks;

/**
 * @author omnya
 *
 */
@Repository("ISemesterWeeksRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SemesterWeeksRepositoryImpl implements ISemesterWeeksRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public SemesterWeeks add(SemesterWeeks week) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
			try{
				   tx = sess.beginTransaction();
					     sess.save(week);
					     tx.commit();
					     return week;
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
	public boolean remove(Integer id) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			SemesterWeeks week=getById(id);
			session.delete(week);
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

	@Override
	public SemesterWeeks update(SemesterWeeks week) {
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     sess.update(week);
		     tx.commit();
		     return week;
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
	public List<SemesterWeeks> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SemesterWeeks.getAll");

			@SuppressWarnings("unchecked")
			List<SemesterWeeks> results = query.list();
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
	public SemesterWeeks getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("SemesterWeeks.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<SemesterWeeks> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<SemesterWeeks> getBySemesterAndyear(Integer sem, Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SemesterWeeks.getBySemesterAndyear").setInteger("sem", sem)
					.setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<SemesterWeeks> results = query.list();
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
	public List<SemesterWeeks> getActiveBySemesterAndyear(Integer sem, Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SemesterWeeks.getActiveBySemesterAndyear").setInteger("sem", sem)
					.setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<SemesterWeeks> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting forms ");
				ex.printStackTrace();
				return null;
			}
	}

}
