/**
 * 
 */
package main.com.zc.services.domain.booksSys.service.repository;

import java.util.List;
import main.com.zc.services.domain.booksSys.model.BookCopies;
import main.com.zc.services.domain.booksSys.model.IBookCopiesRep;
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
@Repository("IBookCopiesRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BookCopiesRepImpl implements IBookCopiesRep {

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public BookCopies add(BookCopies copy) {
/*		try{
			sessionFactory.getCurrentSession().save(copy);
			return copy;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}*/
		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
			try{
				
				
				
					   tx = sess.beginTransaction();
					     sess.save(copy);
					     tx.commit();
				
				return copy;
			
			}
			catch (Exception e) {
			     if (tx!=null) tx.rollback();
			    e.printStackTrace();
			 return null;
			 }
			 finally {
			     sess.close();
			     
			 }
			
	}

	@Override
	public boolean remove(Integer id) {
/*		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(getById(id));
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}*/
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(id);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		 finally {
			 session.close();
		     
		 }
	}

	@Override
	public BookCopies update(BookCopies copy) {

		Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     sess.update(copy);
		     tx.commit();
		     return copy;
		 }
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		    e.printStackTrace();
		 return null;
		 }
		 finally {
		     sess.close();
		 }
	}

	@Override
	public List<BookCopies> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookCopies.getAll");

			@SuppressWarnings("unchecked")
			List<BookCopies> results = query.list();
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
	public List<BookCopies> getAllFree() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookCopies.getAllFree");

			@SuppressWarnings("unchecked")
			List<BookCopies> results = query.list();
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
	public List<BookCopies> getAllHeld() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookCopies.getAllHeld");

			@SuppressWarnings("unchecked")
			List<BookCopies> results = query.list();
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
	public List<BookCopies> getByCourseID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookCopies.getByCourseID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BookCopies> results = query.list();
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
	public List<BookCopies> getByBookID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookCopies.getByBookID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BookCopies> results = query.list();
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
	public BookCopies getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("BookCopies.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BookCopies> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public BookCopies getByBarCode(String code) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("BookCopies.getByBarCode").setString("code", code);

			@SuppressWarnings("unchecked")
			List<BookCopies> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
