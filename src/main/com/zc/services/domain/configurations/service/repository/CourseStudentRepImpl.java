/**
 * 
 */
package main.com.zc.services.domain.configurations.service.repository;

import java.util.List;

import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;

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
@Repository("ICourseStudentRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseStudentRepImpl implements ICourseStudentRep{


	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public CourseStudent add(CourseStudent courseStudent) {
		CourseStudent ob=new  CourseStudent();
		
		try{
			Integer i=(Integer)sessionFactory.getCurrentSession().save(courseStudent);
			courseStudent=getById(i);
			ob=courseStudent;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return ob;
	}
	
	@Override
	public List<CourseStudent> getAll() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseStudent.getAll");

		@SuppressWarnings("unchecked")
		List<CourseStudent> results = query.list();
		return results;
	}

	@Override
	public List<CourseStudent> getByStudentID(Integer id) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseStudent.getByStudentID").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<CourseStudent> results = query.list();
		return results;
	}

	@Override
	public List<CourseStudent> getByCourseID(Integer id) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseStudent.getByCourseID").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<CourseStudent> results = query.list();
		return results;
	}

	@Override
	public CourseStudent getById(Integer id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("CourseStudent.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<CourseStudent> results = query.list();
		return results.get(0);
	}

	@Override
	public CourseStudent getByStudentIDAndByCourseID(Integer studentID,
			Integer courseId) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseStudent.getByStudentIDAndByCourseID").setInteger("studentID", studentID).setInteger("courseId", courseId);

		@SuppressWarnings("unchecked")
		List<CourseStudent> results = query.list();
		return results.get(0);
	}

	@Override
	public boolean remove(CourseStudent obj) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(obj);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	@Override
	public List<CourseStudent> getByStudentIDAndSemesterAndYear(Integer id,
			Integer semester, Integer year) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseStudent.getByStudentIDAndSemesterAndYear").setInteger("studentID", id).
				setInteger("semester", semester).setInteger("year", year);
				;

		@SuppressWarnings("unchecked")
		List<CourseStudent> results = query.list();
		return results;
	}

	@Override
	public List<CourseStudent> getBySemesterAndYear(Integer semester,
			Integer year) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseStudent." +
				"getBySemesterAndYear").
				setInteger("semester", semester).setInteger("year", year);
				;

		@SuppressWarnings("unchecked")
		List<CourseStudent> results = query.list();
		return results;
	}

	@Override
	public List<CourseStudent> getByFacultyIDAndSemesterAndYear(
			Integer facultyID, Integer semester, Integer year) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseStudent." +
				"getByFacultyIDAndSemesterAndYear").setInteger("facultyID", facultyID).
				setInteger("semester", semester).setInteger("year", year);
				;

		@SuppressWarnings("unchecked")
		List<CourseStudent> results = query.list();
		return results;
	}
	@Override
public boolean delete (Integer semester,Integer year){
	Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
	try {
	  // your code
	  String hql = "delete from CourseStudent d where d.course.semester = :semester" +
					" And d.course.year = :year";
	  Query query = sessionFactory.getCurrentSession().createQuery(hql);
	 query.setInteger("semester",semester);
	  query.setInteger("year", year);
	  System.out.println(query.executeUpdate());
	  // your code end

	  transaction.commit();
	  return true;
	} catch (Throwable t) {
	  transaction.rollback();
	  throw t;
	}
}

	@Override
	public boolean removelst(List<CourseStudent> isbefore) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		
		try {
			
			for(CourseStudent obj:isbefore){
			session.delete(obj);
			
			
			}
		
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		finally{
			tx1.commit();
			session.close();
			
		}
	}
}
