/**
 * 
 */
package main.com.zc.services.domain.booksSys.service.repository;

import java.util.List;
import main.com.zc.services.domain.booksSys.model.Book;
import main.com.zc.services.domain.booksSys.model.IBookRepository;
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
@Repository("IBookRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BookRepositoryImpl implements IBookRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	
	@Override
	public Book add(Book book) {
		try{
			sessionFactory.getCurrentSession().save(book);
			return book;
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
	public Book update(Book book) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(book);
			tx1.commit();
			session.close();
			return book;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Book> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Book.getAll");

			@SuppressWarnings("unchecked")
			List<Book> results = query.list();
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
	public Book getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("Book.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<Book> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}
	

	@Override
	public List<Book> getPendingBook() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Book.getPendingBook");

			@SuppressWarnings("unchecked")
			List<Book> results = query.list();
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
	public List<Book> getConfirmedBook() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Book.getConfirmedBook");

			@SuppressWarnings("unchecked")
			List<Book> results = query.list();
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
