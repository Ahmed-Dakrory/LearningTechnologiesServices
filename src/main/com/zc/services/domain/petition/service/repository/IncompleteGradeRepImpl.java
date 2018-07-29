/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import main.com.zc.services.domain.petition.model.IIncompleteGradeRep;
import main.com.zc.services.domain.petition.model.IncompleteGrade;

/**
 * @author omnya
 *
 */
@Repository("IIncompleteGradeRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class IncompleteGradeRepImpl implements IIncompleteGradeRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	
	@Override
	public IncompleteGrade add(IncompleteGrade form) {
		try{
			sessionFactory.getCurrentSession().save(form);
			return form;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean remove(Integer id) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(getById(id));
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public IncompleteGrade update(IncompleteGrade form) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(form);
			tx1.commit();
			session.close();
			return form;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<IncompleteGrade> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getAll");

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
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
	public IncompleteGrade getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("IncompleteGrade.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<IncompleteGrade> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<IncompleteGrade> getPendinByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getPendingByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<IncompleteGrade> getPendingByInstructor(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getPendingByInstructor").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<IncompleteGrade> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<IncompleteGrade> getIncompleteGradeFormJob() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"IncompleteGrade.getPendingJob");
		return query.list();
	}

	@Override
	public List<IncompleteGrade> getOldByInstructor(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getOldByInstructor").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<IncompleteGrade> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
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
	public List<IncompleteGrade> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
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
	public List<IncompleteGrade> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IncompleteGrade.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<IncompleteGrade> results = query.list();
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
