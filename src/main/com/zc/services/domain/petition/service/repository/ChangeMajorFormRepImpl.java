/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;

import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
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
@Repository("IChangeMajorFormRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class ChangeMajorFormRepImpl implements IChangeMajorFormRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public ChangeMajorForm add(ChangeMajorForm form) {
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
	public ChangeMajorForm update(ChangeMajorForm form) {
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
	public List<ChangeMajorForm> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeMajorForm.getAll");

			@SuppressWarnings("unchecked")
			List<ChangeMajorForm> results = query.list();
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
	public ChangeMajorForm getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ChangeMajorForm.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeMajorForm> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ChangeMajorForm> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeMajorForm.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeMajorForm> results = query.list();
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
	public List<ChangeMajorForm> getPendingByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeMajorForm.getPendingByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeMajorForm> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	public List<ChangeMajorForm> getDeanPendingChangeMajorForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("ChangeMajorForm.getDeanPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<ChangeMajorForm> results = query.list();
		return results;
		}

	@Override
	public List<ChangeMajorForm> getAdmissionHeadPendingChangeMajorForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("ChangeMajorForm.getAdHeadPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<ChangeMajorForm> results = query.list();
		return results;
		}

	@Override
	public List<ChangeMajorForm> getAdmissionDeptPendingChangeMajorForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("ChangeMajorForm.getAdDeptPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<ChangeMajorForm> results = query.list();
		return results;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<PendingPetitionCountObject> getInstructorPendingChangMajorPetition(boolean forDailyMAil ) {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"ChangeMajorForm.getInstructorPending").setBoolean("forDailyMAil", forDailyMAil);
		return query.list();
	}

	@Override
	public List<ChangeMajorForm> getChangeMajorFormJob() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"ChangeMajorForm.getPendingJob");
		return query.list();
	}

	@Override
	public List<ChangeMajorForm> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeMajorForm.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeMajorForm> results = query.list();
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
	public List<ChangeMajorForm> getChangeMajorFormHistory(Integer studentId,
			String studentName) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"ChangeMajorForm.getHistory").setInteger("searchType", (studentId == null && studentName == null)? 2:(studentId == null)?0:1).setInteger("studentId", (studentId !=null)?studentId:0).setString( "studentName",(studentName!=null)? "%"+studentName+"%":"");
		return query.list();
	}

	@Override
	public List<ChangeMajorForm> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeMajorForm.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ChangeMajorForm> results = query.list();
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
	public List<ChangeMajorForm> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeMajorForm.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ChangeMajorForm> results = query.list();
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
	public List<ChangeMajorForm> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeMajorForm.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ChangeMajorForm> results = query.list();
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
