/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;
import main.com.zc.services.domain.survey.model.DeclarationOfMinor;
import main.com.zc.services.domain.survey.model.IDeclarationOfMinorRep;
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
@Repository("IDeclarationOfMinorRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeclarationOfMinorRepImpl implements IDeclarationOfMinorRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public DeclarationOfMinor add(DeclarationOfMinor form) {
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
	public DeclarationOfMinor update(DeclarationOfMinor form) {
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
	public List<DeclarationOfMinor> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfMinor.getAll");

			@SuppressWarnings("unchecked")
			List<DeclarationOfMinor> results = query.list();
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
	public DeclarationOfMinor getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfMinor.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DeclarationOfMinor> results = query.list();
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
	public DeclarationOfMinor getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("DeclarationOfMinor.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DeclarationOfMinor> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<DeclarationOfMinor> getByMinorID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfMinor.getByMinorID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DeclarationOfMinor> results = query.list();
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
	public List<DeclarationOfMinor> getAllByYearAndSemester(
			Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfMinor.getAllByYearAndSemester").setInteger("year", year)
					.setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<DeclarationOfMinor> results = query.list();
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
	public List<DeclarationOfMinor> getAllByMinorIDAndYearAndSemester(
			Integer id, Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfMinor.getAllByMinorIDAndYearAndSemester").
					setInteger("year", year)
					.setInteger("semester", semester)
					.setInteger("id",id);

			@SuppressWarnings("unchecked")
			List<DeclarationOfMinor> results = query.list();
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
	public DeclarationOfMinor getByStudentIDAndYearAndSemester(
			Integer id, Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfMinor.getByStudentIDAndYearAndSemester").setInteger("id", id).
					setInteger("year", year).setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<DeclarationOfMinor> results = query.list();
			if(results.size()>0) {
				return results.get(0);
			}else {
				return null;
			}
			
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

}
