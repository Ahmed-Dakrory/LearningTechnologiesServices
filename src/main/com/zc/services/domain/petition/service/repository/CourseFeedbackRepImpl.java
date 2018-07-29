/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;


/**
 * @author Omnya Alaa
 *
 */
@Repository("ICoursePetitionRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseFeedbackRepImpl implements ICoursePetitionRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public CoursePetition add(CoursePetition coursePet) {
		sessionFactory.getCurrentSession().save(coursePet);
		return coursePet;
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
	public CoursePetition update(CoursePetition CoursePetition) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(CoursePetition);
		tx1.commit();
		session.close();
		return CoursePetition;
	}

	@Override
	public List<CoursePetition> getAll() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CoursePetition.getAll");

		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public CoursePetition getById(Integer id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results.get(0);
	}

	@Override
	public List<CoursePetition> getByPersonID(Integer id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getByPersonID").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public List<CoursePetition> getByStatus(String status) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getByStatus").setString("status", status);

		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public List<CoursePetition> getByDate(Calendar submittedDate) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getByDate").setCalendar("submittedDate", submittedDate);

		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public List<CoursePetition> getByCourseID(Integer id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getByCourseID").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public List<CoursePetition> getByCourseCoordinatorIDPending(Integer id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getByCourseCoordinatorIDPending").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public List<CoursePetition> getDeanPendingCoursePetition(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getDeanPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public List<CoursePetition> getAdmissionHeadPendingCoursePetition(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getAdHeadPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public List<CoursePetition> getAdmissionDeptPendingCoursePetition(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getAdDeptPending").setBoolean("forDailyMAil", forDailyMAil);
		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PendingPetitionCountObject> getInstructorPendingCoursePetition(boolean forDailyMAil ) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"CoursePetition.getInstructorPending").setBoolean("forDailyMAil", forDailyMAil);
		return query.list();
	}

	@Override
	public List<CoursePetition> getCoursePetitionJob() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"CoursePetition.getPendingJob");
		return query.list();
	}

	@Override
	public List<CoursePetition> getByCourseCoordinatorIDOld(Integer id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CoursePetition.getByCourseCoordinatorIDOld").setInteger("id", id);
		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}

	@Override
	public List<CoursePetition> getCoursePetitionHistory(Integer studentId,
			String studentName) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery(
				"CoursePetition.getHistory").setInteger("searchType", (studentId == null && studentName == null)? 2:(studentId == null)?0:1).setInteger("studentId", (studentId !=null)?studentId:0).setString( "studentName",(studentName!=null)? "%"+studentName+"%":"");
		return query.list();
	}

	@Override
	public List<CoursePetition> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CoursePetition.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<CoursePetition> results = query.list();
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
	public List<CoursePetition> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CoursePetition.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<CoursePetition> results = query.list();
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
	public List<CoursePetition> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CoursePetition.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<CoursePetition> results = query.list();
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
	public List<CoursePetition> getAllAuditing() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CoursePetition.getAuditingPetitions");

		@SuppressWarnings("unchecked")
		List<CoursePetition> results = query.list();
		return results;
	}
	@Override
	public List<CoursePetition> getPendingAndAuditingAcademicPetitionsAdmissionDept() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CoursePetition.getPendingAndAuditing");

			@SuppressWarnings("unchecked")
			List<CoursePetition> results = query.list();
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
