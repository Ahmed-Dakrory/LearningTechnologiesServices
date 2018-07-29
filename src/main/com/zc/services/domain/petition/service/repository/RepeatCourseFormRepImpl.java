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

import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;

/**
 * @author omnya
 *
 */
@Repository("IRepeatCourseFormRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RepeatCourseFormRepImpl implements IRepeatCourseFormRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	
	@Override
	public RepeatCourseForm add(RepeatCourseForm form) {
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
	public RepeatCourseForm update(RepeatCourseForm form) {
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
	public List<RepeatCourseForm> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getAll");

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
	public RepeatCourseForm getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("RepeatCourseForm.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<RepeatCourseForm> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
	public List<RepeatCourseForm> getPendinByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getPendinByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
	public List<RepeatCourseForm> getRepeatCourseFormJob() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"RepeatCourseForm.getPendingJob");
		return query.list();
	}

	@Override
	public List<RepeatCourseForm> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
	public List<RepeatCourseForm> getRepeatCourseFormHistory(Integer studentId,
			String studentName) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"RepeatCourseForm.getHistory").setInteger("searchType", (studentId == null && studentName == null)? 2:(studentId == null)?0:1).setInteger("studentId", (studentId !=null)?studentId:0).setString( "studentName",(studentName!=null)? "%"+studentName+"%":"");
		return query.list();
	}

	@Override
	public List<RepeatCourseForm> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
	public List<RepeatCourseForm> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
	public List<RepeatCourseForm> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
	public List<RepeatCourseForm> getAllAuditing() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getAuditingPetitions");

		@SuppressWarnings("unchecked")
		List<RepeatCourseForm> results = query.list();
		return results;
	}
	@Override
	public List<RepeatCourseForm> getPendingAdmissionDept() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getPendingAdmissionDept");

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
	public List<RepeatCourseForm> getPendingAuditingAdmissionDept() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("RepeatCourseForm.getPendingAndAuditing");

			@SuppressWarnings("unchecked")
			List<RepeatCourseForm> results = query.list();
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
