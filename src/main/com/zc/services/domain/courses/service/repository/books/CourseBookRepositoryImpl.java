/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.books;

import java.util.List;

import main.com.zc.services.domain.courses.model.books.CourseBookRepository;
import main.com.zc.services.domain.courses.model.books.CourseBooks;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author A7med Al-Dakrory
 *
 */
@Repository
@Transactional
public class CourseBookRepositoryImpl implements CourseBookRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<CourseBooks> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CourseBooks.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CourseBooks> results=query.list();
		   return results;
	}

	@Override
	public CourseBooks addbookForCourse(CourseBooks book) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(book);
			tx1.commit();
			session.close(); 
			return book; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CourseBooks> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CourseBooks.getAll");

				 @SuppressWarnings("unchecked")
				List<CourseBooks> results=query.list();
				   return results;
	}

	
	@Override
	public boolean delete(CourseBooks data) {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(data);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public CourseBooks getById(int id) {
		// TODO Auto-generated method stub
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("CourseBooks.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<CourseBooks> results=query.list();
		   return results.get(0);
	}
	

}
