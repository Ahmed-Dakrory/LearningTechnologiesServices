/**
 * 
 */
package main.com.zc.services.domain.booksSys.service.repository;

import java.util.List;

import main.com.zc.services.domain.booksSys.model.BooksLogs;
import main.com.zc.services.domain.booksSys.model.IBooksLogsRepository;

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
@Repository("IBooksLogsRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BooksLogsRepositoryImpl implements IBooksLogsRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public BooksLogs add(BooksLogs obj) {
		try{
			sessionFactory.getCurrentSession().save(obj);
			return obj;
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
	public BooksLogs update(BooksLogs obj) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(obj);
			tx1.commit();
			session.close();
			return obj;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BooksLogs> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BooksLogs.getAll");

			@SuppressWarnings("unchecked")
			List<BooksLogs> results = query.list();
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
	public BooksLogs getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BooksLogs.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BooksLogs> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting forms ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<BooksLogs> getByUserName(String user) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BooksLogs.getByUserName").setString("user", user);

			@SuppressWarnings("unchecked")
			List<BooksLogs> results = query.list();
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
	public List<BooksLogs> getByBookID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BooksLogs.getByBookID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BooksLogs> results = query.list();
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
