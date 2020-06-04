package main.com.zc.services.domain.data.service.repository;

/**
 * 
 */

import java.util.List;

import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;

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
@Repository("ICourse_InstructorRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class Course_InstructorRepositoryImpl implements ICourse_InstructorRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public int add(Courses_Instructors courseInstructor) {
		sessionFactory.getCurrentSession().save(courseInstructor);
		return courseInstructor.getId();
	}

	@Override
	public boolean remove(Courses_Instructors courseInstructor) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(courseInstructor);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Courses_Instructors update(Courses_Instructors courseInstructor) {
		session = sessionFactory.openSession();
		Transaction tx1 = session.beginTransaction();
		session.update(courseInstructor);
		tx1.commit();
		session.close();
		return courseInstructor;
	}

	@Override
	public List<Courses_Instructors> getAll() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("Courses_Instructors.getAll");

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results;
	}

	@Override
	public Courses_Instructors getById(int id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getById").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results.get(0);
	}

	@Override
	public List<Courses_Instructors> getByCourseId(int id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getByCourseId").setInteger("courseID", id);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results;
	}

	@Override
	public List<Courses_Instructors> getByInstructorId(int id) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getByInstructorId").setInteger("instructorID", id);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results;
	}
	@Override
	public List<Courses_Instructors> getByInstructorIdAndYearAndSemester(int id , Integer year, Integer semester) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getByInstructorIdAndYearAndSemester").setInteger("instructorID", id).setInteger("year", year).setInteger("semester", semester);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results;
	}
	@Override
	public Courses_Instructors getByInstructorMail(String mail) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getByInstructorMail").setString("mail", mail);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results.get(0);
	}

	@Override
	public List<Courses_Instructors> getByInstructorMaillst(String mail) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getByInstructorMail").setString("mail", mail);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results;
	}

	@Override
	public Courses_Instructors getByCourseIdAndInsId(int courseID,
			int instructorID) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getByCourseIdAndInsId").setInteger("courseID", courseID).setInteger("instructorID", instructorID);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		if(results!=null ) {
			if(results.size()>0 ) {
				return results.get(0);
			}
		}
		return null;
	}

	@Override
	public List<Courses_Instructors> getTAsByCourseID(int courseID) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getTAsByCourseID").setInteger("courseID",courseID);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results;
	}

	@Override
	public List<Courses_Instructors> getInstructorsByCourseID(int courseID) {
		Query query = sessionFactory.getCurrentSession()
				.getNamedQuery("Courses_Instructors.getInstructorsByCourseID").setInteger("courseID",courseID);

		@SuppressWarnings("unchecked")
		List<Courses_Instructors> results = query.list();
		return results;
	}

}
