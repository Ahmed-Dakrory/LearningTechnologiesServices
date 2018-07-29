/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;
import main.com.zc.services.domain.petition.model.IskipMajorHeadCoursesRep;
import main.com.zc.services.domain.petition.model.SkipMajorHeadCourses;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author omnya
 *
 */
@Repository("IskipMajorHeadCoursesRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SkipMajorHeadCoursesRepImpl implements IskipMajorHeadCoursesRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public SkipMajorHeadCourses add(SkipMajorHeadCourses form) {
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
	public SkipMajorHeadCourses update(SkipMajorHeadCourses form) {
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
	public List<SkipMajorHeadCourses> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("SkipMajorHeadCourses.getAll");

			@SuppressWarnings("unchecked")
			List<SkipMajorHeadCourses> results = query.list();
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
	public SkipMajorHeadCourses getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("SkipMajorHeadCourses.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<SkipMajorHeadCourses> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public SkipMajorHeadCourses getByCourseID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("SkipMajorHeadCourses.getByCourseID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<SkipMajorHeadCourses> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
