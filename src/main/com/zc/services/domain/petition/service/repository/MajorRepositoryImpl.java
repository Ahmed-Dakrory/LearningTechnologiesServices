/**
 * 
 */
package main.com.zc.services.domain.petition.service.repository;

import java.util.ArrayList;
import java.util.List;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.Majors;
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
@Repository("IMajorRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MajorRepositoryImpl implements IMajorRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public Majors add(Majors major) {
		try{
		sessionFactory.getCurrentSession().save(major);
		return major;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean remove(int id) {
		Transaction tx1 = null;
		try {
			session = sessionFactory.openSession();
			 tx1 = session.beginTransaction();
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
	public Majors update(Majors major) {
	try{
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(major);
		tx1.commit();
		
		return major;
	}
	catch(Exception ex)
	{
		
		ex.printStackTrace();
		return null;
	}
	finally {
		session.close();
	}
	}

	@Override
	public List<Majors> getAll() {
		try{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Majors.getAll");

		@SuppressWarnings("unchecked")
		List<Majors> results = query.list();
		List<Majors> temp = new ArrayList<Majors>();
		for(int i=0;i<results.size();i++)
		{
			if(results.get(i).getHidden()==null)
				temp.add(results.get(i));
			
			else if(results.get(i).getHidden()!=true)
				temp.add(results.get(i));
				
		}
		return temp;
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting majors ");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Majors getById(int id) {
		try{
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Majors.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<Majors> results = query.list();
		return results.get(0);
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<Majors> getByInsID(int id) {
		try{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Majors.getByInsID").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<Majors> results = query.list();
		return results;
		}
		catch(Exception ex)
		{
			System.out.println("error in getting major by instructor");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Majors> getAllOLdNew() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Majors.getAll");

			@SuppressWarnings("unchecked")
			List<Majors> results = query.list();
			
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting majors ");
				ex.printStackTrace();
				return null;
			}
	}

}
