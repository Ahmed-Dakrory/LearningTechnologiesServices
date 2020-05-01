/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;

import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.Icourse_replacement_formFormRep;
import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author omnya.alaa
 *
 */
@Repository("Icourse_replacement_formFormRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class course_replacement_formFormRepImpl implements Icourse_replacement_formFormRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public course_replacement_formForm add(course_replacement_formForm form) {
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
	public course_replacement_formForm update(course_replacement_formForm form) {
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
	public List<course_replacement_formForm> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("course_replacement_formForm.getAll");

			@SuppressWarnings("unchecked")
			List<course_replacement_formForm> results = query.list();
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
	public course_replacement_formForm getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("course_replacement_formForm.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<course_replacement_formForm> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<course_replacement_formForm> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("course_replacement_formForm.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<course_replacement_formForm> results = query.list();
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
	public List<course_replacement_formForm> getPendingByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("course_replacement_formForm.getPendingByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<course_replacement_formForm> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	public List<course_replacement_formForm> getDeanPendingcourse_replacement_formForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("course_replacement_formForm.getDeanPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<course_replacement_formForm> results = query.list();
		return results;
		}

	@Override
	public List<course_replacement_formForm> getAdmissionHeadPendingcourse_replacement_formForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("course_replacement_formForm.getAdHeadPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<course_replacement_formForm> results = query.list();
		return results;
		}

	@Override
	public List<course_replacement_formForm> getAdmissionDeptPendingcourse_replacement_formForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("course_replacement_formForm.getAdDeptPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<course_replacement_formForm> results = query.list();
		return results;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<PendingPetitionCountObject> getDeanOfAcadPendingcourse_replacement_formPetition(boolean forDailyMAil ) {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"course_replacement_formForm.getDeanAcadPending").setBoolean("forDailyMAil", forDailyMAil);
		return query.list();
	}

	@Override
	public List<course_replacement_formForm> getcourse_replacement_formFormJob() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"course_replacement_formForm.getPendingJob");
		return query.list();
	}

	@Override
	public List<course_replacement_formForm> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("course_replacement_formForm.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<course_replacement_formForm> results = query.list();
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
	public List<course_replacement_formForm> getcourse_replacement_formFormHistory(Integer studentId,
			String studentName) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"course_replacement_formForm.getHistory").setInteger("searchType", (studentId == null && studentName == null)? 2:(studentId == null)?0:1).setInteger("studentId", (studentId !=null)?studentId:0).setString( "studentName",(studentName!=null)? "%"+studentName+"%":"");
		return query.list();
	}

	@Override
	public List<course_replacement_formForm> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("course_replacement_formForm.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<course_replacement_formForm> results = query.list();
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
	public List<course_replacement_formForm> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("course_replacement_formForm.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<course_replacement_formForm> results = query.list();
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
	public List<course_replacement_formForm> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("course_replacement_formForm.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<course_replacement_formForm> results = query.list();
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
	public List<PendingPetitionCountObject> getInstructorPendingcourse_replacement_formPetition(Integer employID, boolean forDailyMAil) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"course_replacement_formForm.getInstructorPending").setInteger("employID", employID).setBoolean("forDailyMAil", forDailyMAil);
		return query.list();
	}
}
