/**
 * 
 */
package main.com.zc.services.domain.data.service.repository;

import java.util.List;
import main.com.zc.services.domain.data.model.IStudentProfileRep;
import main.com.zc.services.domain.data.model.StudentProfile;
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
@Repository("IStudentProfileRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentProfileRepImpl implements IStudentProfileRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	
	@Override
	public StudentProfile add(StudentProfile form) {
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
	public StudentProfile update(StudentProfile form) {
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
	public List<StudentProfile> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("StudentProfile.getAll");

			@SuppressWarnings("unchecked")
			List<StudentProfile> results = query.list();
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
	public StudentProfile getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("StudentProfile.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<StudentProfile> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public  List<StudentProfile> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("StudentProfile.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<StudentProfile> results = query.list();
			
			return results;
		}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<StudentProfile> getBySemesterAndYear(Integer sem, Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("StudentProfile.getBySemesterAndYear").setInteger("sem", sem).setInteger("year", year);

			@SuppressWarnings("unchecked")
			List<StudentProfile> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public StudentProfile getBySemesterAndYearAndStudentId(Integer sem,
			Integer year, Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("StudentProfile.getBySemesterAndYearAndStudentId").setInteger("sem", sem).setInteger("year", year).setInteger("id",id);

			@SuppressWarnings("unchecked")
			List<StudentProfile> results = query.list();
			if(results.size()>0) {

				return results.get(0);
			}else {
				return null;
			}
			}
			catch(Error ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
