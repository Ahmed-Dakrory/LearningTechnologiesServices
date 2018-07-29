/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.domain.survey.model.IConcentrationRep;
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
@Repository("IConcentrationRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ConcentrationRepImpl implements IConcentrationRep {

	
	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public Concentration add(Concentration form) {
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
		Transaction tx1 =null;
		try {
			session = sessionFactory.openSession();
			tx1=session.beginTransaction();
			session.delete(getById(id));
			tx1.commit();
		
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx1.rollback();
			return false;
		}
		finally{
			session.close();
		}
	}
	@Override
	public Concentration update(Concentration form) {
		
		Transaction tx1 =null;
		try{
			session = sessionFactory.openSession();
			tx1=session.beginTransaction();
			session.update(form);
			tx1.commit();
			
			return form;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			tx1.rollback();
			return null;
		}
		finally{
			session.close();
		}
	}
	@Override
	public List<Concentration> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Concentration.getAll");

			@SuppressWarnings("unchecked")
			List<Concentration> results = query.list();
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
	public Concentration getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("Concentration.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<Concentration> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}
	@Override
	public List<Concentration> getByParentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Concentration.getByParentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<Concentration> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting concentrations ");
				ex.printStackTrace();
				return null;
			}
	}
	@Override
	public boolean hide(Integer id) {
		
		Transaction tx1 =null;
		try{
			session = sessionFactory.openSession();
			tx1=session.beginTransaction();
			Concentration oldCon=getById(id);
			oldCon.setParent(null);
			session.update(oldCon);
			tx1.commit();
			
			return true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			tx1.rollback();
			return false;
		}
		finally{
			session.close();
		}
	}
	
	
}
