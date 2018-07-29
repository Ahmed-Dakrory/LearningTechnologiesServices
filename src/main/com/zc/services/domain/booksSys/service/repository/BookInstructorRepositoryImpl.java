/**
 * 
 */
package main.com.zc.services.domain.booksSys.service.repository;

import java.util.List;
import main.com.zc.services.domain.booksSys.model.BookInstructor;
import main.com.zc.services.domain.booksSys.model.IBookInstructorRepository;
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
@Repository("IBookInstructorRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BookInstructorRepositoryImpl implements IBookInstructorRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public BookInstructor add(BookInstructor obj) {
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
	public BookInstructor update(BookInstructor obj) {
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
	public List<BookInstructor> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookInstructor.getAll");

			@SuppressWarnings("unchecked")
			List<BookInstructor> results = query.list();
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
	public BookInstructor getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookInstructor.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BookInstructor> results = query.list();
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
	public List<BookInstructor> getByInsID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookInstructor.getByInsID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BookInstructor> results = query.list();
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
	public List<BookInstructor> getByBarCodeAndInsID(String barCode,
			Integer insID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookInstructor.getByBarCodeAndInsID").setString("code", barCode).setInteger("id", insID);

			@SuppressWarnings("unchecked")
			List<BookInstructor> results = query.list();
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
	public List<BookInstructor> getByBarCode(String barCode) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookInstructor.getByBarCode").setString("code", barCode);

			@SuppressWarnings("unchecked")
			List<BookInstructor> results = query.list();
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
