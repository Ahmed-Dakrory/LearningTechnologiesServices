/**
 * 
 */
package main.com.zc.services.domain.booksSys.service.repository;

import java.util.List;

import main.com.zc.services.domain.booksSys.model.BookStudent;
import main.com.zc.services.domain.booksSys.model.IBookStudentRepository;

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
@Repository("IBookStudentRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BookStudentRepositoryImpl implements IBookStudentRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	
	@Override
	public BookStudent add(BookStudent obj) {
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
	public BookStudent update(BookStudent obj) {
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
	public List<BookStudent> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookStudent.getAll");

			@SuppressWarnings("unchecked")
			List<BookStudent> results = query.list();
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
	public BookStudent getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookStudent.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BookStudent> results = query.list();
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
	public List<BookStudent> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookStudent.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<BookStudent> results = query.list();
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
	public List<BookStudent> getByBarCodeAndStudentID(String barCode,
			Integer studentID) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookStudent.getByBarCodeAndStudentID").setString("code", barCode).setInteger("id", studentID);

			@SuppressWarnings("unchecked")
			List<BookStudent> results = query.list();
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
	public List<BookStudent> getByBarCode(String barCode) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("BookStudent.getByBarCode").setString("code", barCode);

			@SuppressWarnings("unchecked")
			List<BookStudent> results = query.list();
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


