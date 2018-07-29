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
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;

/**
 * @author omnya
 *
 */
@Repository("IAddDropFormRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AddDropFormRepositoryImpl implements IAddDropFormRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public DropAddForm add(DropAddForm form) {
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
	public DropAddForm update(DropAddForm form) {
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
	public List<DropAddForm> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getAll");

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
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
	public DropAddForm getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("DropAddForm.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<DropAddForm> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
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
	public List<DropAddForm> getDeanPendingDropAddForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("DropAddForm.getDeanPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<DropAddForm> results = query.list();
		return results;
		}

	@Override
	public List<DropAddForm> getAdmissionHeadPendingDropAddForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("DropAddForm.getAdHeadPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<DropAddForm> results = query.list();
		return results;
		}

	@Override
	public List<DropAddForm> getAdmissionDeptPendingDropAddForm(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("DropAddForm.getAdDeptPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<DropAddForm> results = query.list();
		return results;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<PendingPetitionCountObject> getInstructorPendingDropAddPetition(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"DropAddForm.getInstructorPending").setBoolean("forDailyMAil", forDailyMAil);
		return query.list();
	}

	@Override
	public List<DropAddForm> getPendingByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getPendingByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of Program Advisor");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<DropAddForm> getDropAddFormJob() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"DropAddForm.getPendingJob");
		return query.list();
	}

	@Override
	public List<DropAddForm> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of Program Advisor");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<DropAddForm> getDropAddFormHistory(Integer studentId,
			String studentName) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"DropAddForm.getHistory").setInteger("searchType", (studentId == null && studentName == null)? 2:(studentId == null)?0:1).setInteger("studentId", (studentId !=null)?studentId:0).setString( "studentName",(studentName!=null)? "%"+studentName+"%":"");
		return query.list();
	}

	@Override
	public List<DropAddForm> getByDropppedCourseIns(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getByDropppedCourseIns").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of Program Advisor");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<DropAddForm> getInstructorCountPending(Integer instructorID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getInstructorCountPending").setInteger("id", instructorID);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of Program Advisor");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<DropAddForm> getPendingDean() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getPendingDean");

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting forms ");
				ex.printStackTrace();
				return null;}
	}

	@Override
	public List<DropAddForm> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
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
	public List<DropAddForm> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
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
	public List<DropAddForm> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DropAddForm.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<DropAddForm> results = query.list();
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
