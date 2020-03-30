/**
 * 
 */
package main.com.zc.services.domain.data.service.repository;

import java.util.List;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Omnya Alaa
 * 
 */
@Repository("ICoursesRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CoursesRepositoryImpl implements ICoursesRepository {

	@Autowired
	private SessionFactory sessionFactory;
	Session session;

	@Override
	public int add(Courses course) {
		sessionFactory.getCurrentSession().save(course);
		return course.getId();
	}

	@Override
	public boolean remove(Courses course) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(course);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			session.close();
			return false;
		}
	}

	@Override
	public Courses update(Courses course) {
		try{session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(course);
		tx1.commit();
		session.close();
		return course;
		}
		catch(Exception e)
		{
			session.close();
			e.printStackTrace();
			return null;
			
		}
	}

	@Override
	public List<Courses> getAll() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Courses.getAll");

		@SuppressWarnings("unchecked")
		List<Courses> results = query.list();
		return results;
	}

	@Override
	public Courses getById(int id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<Courses> results = query.list();
		return results.get(0);
	}

	@Override
	public Courses getByName(String name) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses.getByName").setString("name", name);

		@SuppressWarnings("unchecked")
		List<Courses> results = query.list();
		return results.get(0);
	}

	@Override
	public List<Courses> getByCourseCoordinatorID(String mail) {
		try{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Courses.getByCourseCoordinatorID").setString("mail",mail);

		@SuppressWarnings("unchecked")
		List<Courses> results = query.list();
		return results;
		}
		catch(Exception ex)
		{
			System.out.println("-------------Error in getting course by Course Coordinator");
			ex.printStackTrace();
			return null;
		}
		

	}

	@Override
	public Courses getByNameAndYearAndSemester(String name, Integer year,
			Integer semester) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Courses.getByNameAndYearAndSemester").setString("name",name).setInteger("year",year).
					setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<Courses> results = query.list();
			if(results.size()>0) {

				return results.get(0);	
			}else {
				return null;
			}
			}
			catch(Exception ex)
			{
				System.out.println("-------------Error in getting course by Course name and year and semester");
				ex.printStackTrace();
				return null;
			}
			
	}

	@Override
	public List<Courses> getByYearAndSemester(Integer semester, Integer year) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Courses.getByYearAndSemester").setInteger("year",year).
					setInteger("semester", semester);

			@SuppressWarnings("unchecked")
			List<Courses> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("-------------Error in getting course by Course name and year and semester");
				ex.printStackTrace();
				return null;
			}
	}

}
