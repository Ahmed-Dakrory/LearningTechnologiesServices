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
import main.com.zc.services.domain.petition.model.ChangeConcentration;
import main.com.zc.services.domain.petition.model.IChangeConcentrationRep;
import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;

/**
 * @author omnya
 *
 */
@Repository("IChangeConcentrationRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ChangeConcentrationRepImpl implements IChangeConcentrationRep {

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public ChangeConcentration add(ChangeConcentration form) {
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
	public ChangeConcentration update(ChangeConcentration form) {
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
	public List<ChangeConcentration> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeConcentration.getAll");

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
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
	public ChangeConcentration getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ChangeConcentration.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ChangeConcentration> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ChangeConcentration.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ChangeConcentration> getPendingByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ChangeConcentration.getPendingByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ChangeConcentration> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ChangeConcentration.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ChangeConcentration> getAdHeadPending(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ChangeConcentration.getAdHeadPendingNow");

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ChangeConcentration> getAdDeptPending(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ChangeConcentration.getAdDeptPending").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}
	
	
	@Override
	public List<ChangeConcentration> getAdDeptPendingNoReminder() {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("ChangeConcentration.getAdDeptPendingNoReminder");

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<PendingPetitionCountObject> getInstructorPendingChangMajorPetition(boolean forDailyMAil) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery(
					"ChangeConcentration.getInstructorPending").setBoolean("forDailyMAil", forDailyMAil);
			return query.list();
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<ChangeConcentration> getOldSummer(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeConcentration.getOldSummer").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
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
	public List<ChangeConcentration> getOldSpring(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeConcentration.getOldSpring").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
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
	public List<ChangeConcentration> getOldFall(Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeConcentration.getOldFall").setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
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
	public List<ChangeConcentration> getPendingByStudentId(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("ChangeConcentration.getStudentPending").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<ChangeConcentration> results = query.list();
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
	public List<ChangeConcentration> getDeanOfAcademicPending(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
