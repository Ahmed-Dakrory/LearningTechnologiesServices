/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.List;

import main.com.zc.services.domain.petition.model.ITAJuniorProgramRep;
import main.com.zc.services.domain.petition.model.TAJuniorProgram;

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
@Repository("ITAJuniorProgramRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TAJuniorProgramRepositoryImpl implements ITAJuniorProgramRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public TAJuniorProgram add(TAJuniorProgram form) {
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
	public TAJuniorProgram update(TAJuniorProgram form) {
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
	public List<TAJuniorProgram> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getAll");

			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public TAJuniorProgram getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("TAJuniorProgram.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<TAJuniorProgram> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getByCourseCoordniatorPending(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getByCourseCoordniatorPending").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getByCourseCoordniatorOld(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getByCourseCoordniatorOld").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getPendingByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getPendingByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getOldByPA(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getOldByPA").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getPendingByDean() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getPendingByDean");

			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getOldByDean() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getOldByDean");
			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getPendingByDeanDashboard() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getPendingByDeanDashboard");
			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getPendingByCoordDashboard(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getPendingByCoordDashboard").setInteger("id",id);
			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getPendingByPADashboard(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getPendingByPADashboard").setInteger("id",id);
			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getPendingJob() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getPendingJob");
			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
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
	public List<TAJuniorProgram> getByCourseID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("TAJuniorProgram.getByCourseID").setInteger("id", id);
			@SuppressWarnings("unchecked")
			List<TAJuniorProgram> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

}
