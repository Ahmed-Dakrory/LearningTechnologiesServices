/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;
import main.com.zc.services.domain.survey.model.IIntendedMajorSurveyRep;
import main.com.zc.services.domain.survey.model.IntendedMajorSurvey;
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
@Repository("IIntendedMajorSurveyRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class IntendedMajorSurveyRepImpl implements IIntendedMajorSurveyRep {

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	
	@Override
	public IntendedMajorSurvey add(IntendedMajorSurvey form) {
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
	public IntendedMajorSurvey update(IntendedMajorSurvey form) {
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
	public List<IntendedMajorSurvey> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IntendedMajorSurvey.getAll");

			@SuppressWarnings("unchecked")
			List<IntendedMajorSurvey> results = query.list();
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
	public IntendedMajorSurvey getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IntendedMajorSurvey.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IntendedMajorSurvey> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public IntendedMajorSurvey getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("IntendedMajorSurvey.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IntendedMajorSurvey> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<IntendedMajorSurvey> getByMajorID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IntendedMajorSurvey.getByMajorID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IntendedMajorSurvey> results = query.list();
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
	public List<IntendedMajorSurvey> getByMajorHead(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IntendedMajorSurvey.getByMajorHead").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<IntendedMajorSurvey> results = query.list();
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
	public List<IntendedMajorSurvey> getByMajorIDAndYearAndSemester(Integer id,
			Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IntendedMajorSurvey.getByMajorIDAndYearAndSemester")
					.setInteger("id", id).setInteger("year", year).setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<IntendedMajorSurvey> results = query.list();
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
	public List<IntendedMajorSurvey> getByYearAndSemester(Integer year,
			Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("IntendedMajorSurvey.getByYearAndSemester")
					.setInteger("year", year).setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<IntendedMajorSurvey> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

}
