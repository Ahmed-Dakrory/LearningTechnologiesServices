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

import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;

/**
 * @author omnya.alaa
 *
 */
@Repository("IOverloadRequestRep")
@Transactional
public class OverloadRequestRepImpl implements IOverloadRequestRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public OverloadRequest add(OverloadRequest form) {
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
	public OverloadRequest update(OverloadRequest form) {
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
	public List<OverloadRequest> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getAll");

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
	public OverloadRequest getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("OverloadRequest.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<OverloadRequest> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
	public List<OverloadRequest> getByCourseCoordniator(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getByCourseCoordniator").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
	public List<OverloadRequest> getPendingByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getPendingByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
	public  List<OverloadRequest> getDeanPendingOverloadRequest(boolean forDailyMAil )  {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"OverloadRequest.getDeanPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<OverloadRequest> results = query.list();
		return results;
	}
	@Override
	public  List<OverloadRequest> getAdmissionHeadPendingOverloadRequest(boolean forDailyMAil )  {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"OverloadRequest.getAdHeadPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<OverloadRequest> results = query.list();
		return results;
	}
	@Override
	public  List<OverloadRequest> getAdmissionDeptPendingOverloadRequest(boolean forDailyMAil )  {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"OverloadRequest.getAdDeptPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<OverloadRequest> results = query.list();
		return results;
	}

	@Override
	public  List<OverloadRequest> getProvostPendingOverloadRequest(boolean forDailyMAil )  {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"OverloadRequest.getProvostPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<OverloadRequest> results = query.list();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PendingPetitionCountObject> getInstructorPendingOverLoadPetition(boolean forDailyMAil )  {
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"OverloadRequest.getInstructorPending").setBoolean("forDailyMAil", forDailyMAil);
		return query.list();
	}

	@Override
	public List<OverloadRequest> getOverloadRequestJob() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"OverloadRequest.getPendingJob");
		return query.list();
	}

	@Override
	public List<OverloadRequest> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
	public List<OverloadRequest> getOverloadRequestHistory(Integer studentId,
			String studentName) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"OverloadRequest.getHistory").setInteger("searchType", (studentId == null && studentName == null)? 2:(studentId == null)?0:1).setInteger("studentId", (studentId !=null)?studentId:0).setString( "studentName",(studentName!=null)? "%"+studentName+"%":"");
		return query.list();
	}

	@Override
	public List<OverloadRequest> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
	public List<OverloadRequest> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
	public List<OverloadRequest> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
	public List<OverloadRequest> getAllAuditing() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getAuditingPetitions");

		@SuppressWarnings("unchecked")
		List<OverloadRequest> results = query.list();
		return results;
	}
	@Override
	public List<OverloadRequest> getPendingAndAuditingOverloadRequestAdmissionDept() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("OverloadRequest.getPendingAndAuditing");

			@SuppressWarnings("unchecked")
			List<OverloadRequest> results = query.list();
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
