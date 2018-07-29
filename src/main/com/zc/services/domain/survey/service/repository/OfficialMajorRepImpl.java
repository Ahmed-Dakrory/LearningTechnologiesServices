/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.domain.survey.model.IOfficialMajorRep;
import main.com.zc.services.domain.survey.model.OfficialMajor;
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
@Repository("IOfficialMajorRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OfficialMajorRepImpl implements IOfficialMajorRep{


	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public OfficialMajor add(OfficialMajor form) {
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
	public OfficialMajor update(OfficialMajor form) {
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
	public List<OfficialMajor> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OfficialMajor.getAll");

			@SuppressWarnings("unchecked")
			List<OfficialMajor> results = query.list();
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
	public OfficialMajor getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OfficialMajor.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OfficialMajor> results = query.list();
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
	public OfficialMajor getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("IntendedMajorSurvey.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OfficialMajor> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<OfficialMajor> getByMajorID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OfficialMajor.getByMajorID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OfficialMajor> results = query.list();
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
	public List<OfficialMajor> getByMajorHead(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OfficialMajor.getByMajorHead").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OfficialMajor> results = query.list();
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
	public List<OfficialMajor> getByMajorIDAndYearAndSemester(Integer id,
			Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OfficialMajor.getByMajorIDAndYearAndSemester")
					.setInteger("id", id).setInteger("year", year).setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<OfficialMajor> results = query.list();
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
	public List<OfficialMajor> getByYearAndSemester(Integer year,
			Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OfficialMajor.getByYearAndSemester")
					.setInteger("year", year).setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<OfficialMajor> results = query.list();
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
	public OfficialMajor getByStudentIDAndYearAndSemester(Integer id,
			Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OfficialMajor.getByStudentIDAndYearAndSemester")
					.setInteger("year", year).setInteger("semester", semester).setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OfficialMajor> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

}
