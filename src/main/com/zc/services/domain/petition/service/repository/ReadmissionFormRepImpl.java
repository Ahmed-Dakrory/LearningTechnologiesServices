/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;

import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.IReadmissionFormRep;
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
@Repository("IReadmissionFormRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class ReadmissionFormRepImpl implements IReadmissionFormRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public ReadmissionForm add(ReadmissionForm form) {
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
	public ReadmissionForm update(ReadmissionForm form) {
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
	public List<ReadmissionForm> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ReadmissionForm.getAll");

			@SuppressWarnings("unchecked")
			List<ReadmissionForm> results = query.list();
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
	public ReadmissionForm getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ReadmissionForm.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ReadmissionForm> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ReadmissionForm> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ReadmissionForm.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ReadmissionForm> results = query.list();
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
	public List<ReadmissionForm> getPendingByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ReadmissionForm.getPendingByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ReadmissionForm> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	public List<ReadmissionForm> getDeanPendingReadmissionForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("ReadmissionForm.getDeanPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<ReadmissionForm> results = query.list();
		return results;
		}

	@Override
	public List<ReadmissionForm> getAdmissionHeadPendingReadmissionForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("ReadmissionForm.getAdHeadPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<ReadmissionForm> results = query.list();
		return results;
		}

	@Override
	public List<ReadmissionForm> getAdmissionDeptPendingReadmissionForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("ReadmissionForm.getAdDeptPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<ReadmissionForm> results = query.list();
		return results;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<PendingPetitionCountObject> getDeanOfAcadPendingReadMissionPetition(boolean forDailyMAil ) {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"ReadmissionForm.getDeanAcadPending").setBoolean("forDailyMAil", forDailyMAil);
		return query.list();
	}

	@Override
	public List<ReadmissionForm> getReadmissionFormJob() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"ReadmissionForm.getPendingJob");
		return query.list();
	}

	@Override
	public List<ReadmissionForm> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ReadmissionForm.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ReadmissionForm> results = query.list();
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
	public List<ReadmissionForm> getReadmissionFormHistory(Integer studentId,
			String studentName) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"ReadmissionForm.getHistory").setInteger("searchType", (studentId == null && studentName == null)? 2:(studentId == null)?0:1).setInteger("studentId", (studentId !=null)?studentId:0).setString( "studentName",(studentName!=null)? "%"+studentName+"%":"");
		return query.list();
	}

	@Override
	public List<ReadmissionForm> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ReadmissionForm.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ReadmissionForm> results = query.list();
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
	public List<ReadmissionForm> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ReadmissionForm.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ReadmissionForm> results = query.list();
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
	public List<ReadmissionForm> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ReadmissionForm.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ReadmissionForm> results = query.list();
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
