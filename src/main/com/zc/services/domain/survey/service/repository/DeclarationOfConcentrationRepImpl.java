/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;
import main.com.zc.services.domain.survey.model.DeclarationOfConcentration;
import main.com.zc.services.domain.survey.model.IDeclarationOfConcentrationRep;
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
@Repository("IDeclarationOfConcentrationRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeclarationOfConcentrationRepImpl implements IDeclarationOfConcentrationRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public DeclarationOfConcentration add(DeclarationOfConcentration form) {
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
	public DeclarationOfConcentration update(DeclarationOfConcentration form) {
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
	public List<DeclarationOfConcentration> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfConcentration.getAll");

			@SuppressWarnings("unchecked")
			List<DeclarationOfConcentration> results = query.list();
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
	public DeclarationOfConcentration getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfConcentration.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DeclarationOfConcentration> results = query.list();
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
	public DeclarationOfConcentration getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("DeclarationOfConcentration.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DeclarationOfConcentration> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<DeclarationOfConcentration> getByConcentrationID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfConcentration.getByConcentrationID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<DeclarationOfConcentration> results = query.list();
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
	public List<DeclarationOfConcentration> getAllByYearAndSemester(
			Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfConcentration.getAllByYearAndSemester").setInteger("year", year)
					.setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<DeclarationOfConcentration> results = query.list();
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
	public List<DeclarationOfConcentration> getAllByConcentrationIDAndYearAndSemester(
			Integer id, Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfConcentration.getAllByConcentrationIDAndYearAndSemester").
					setInteger("year", year)
					.setInteger("semester", semester)
					.setInteger("id",id);

			@SuppressWarnings("unchecked")
			List<DeclarationOfConcentration> results = query.list();
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
	public DeclarationOfConcentration getByStudentIDAndYearAndSemester(
			Integer id, Integer year, Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("DeclarationOfConcentration.getByStudentIDAndYearAndSemester").setInteger("id", id).
					setInteger("year", year).setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<DeclarationOfConcentration> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

}
