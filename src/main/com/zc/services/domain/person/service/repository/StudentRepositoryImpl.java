/**
 * 
 */
package main.com.zc.services.domain.person.service.repository;

import java.util.List;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Omnya
 * 
 */
@Repository("IStudentRepository")
@Transactional
public class StudentRepositoryImpl implements IStudentRepository {

	@Autowired
	private SessionFactory session;

	@Override
	public int add(Student person) {
		session.getCurrentSession().save(person);
		return person.getId();
	}

	@Override
	public List<Student> getAll() {
		Query query = session.getCurrentSession()
				.getNamedQuery("Student.getAll");

		@SuppressWarnings("unchecked")
		List<Student> results = query.list();
		return results;

	}

	@Override
	public Student getPersonById(int id) {
		Query query = session.getCurrentSession()
				.getNamedQuery("Student.getPersonByID").setInteger("id", id);

		@SuppressWarnings("unchecked")
		List<Student> results = query.list();
		return results.get(0);
	}

	@Override
	public Student getPersonByFileNo(int fileNo) {
	try{
		Query query = session.getCurrentSession()
				.getNamedQuery("Student.getPersonByFileNo")
				.setInteger("fileNo", fileNo);

		@SuppressWarnings("unchecked")
		List<Student> results = query.list();
		if (results.size() <= 0)
			return null;
		else
			return results.get(0);
	}
	catch(Exception ex)
	{
		return null;
	}
	}

	@Override
	public Student getPersonByMail(String mail) {
		Query query = session.getCurrentSession()
				.getNamedQuery("Student.getByMail")
				.setString("mail", mail.toLowerCase());
		@SuppressWarnings("unchecked")
		List<Student> results = query.list();
		if (results.size() <= 0)
			return null;
		else
			return results.get(0);
	}

	@Override
	public List<Student> getByLevel(Integer level) {
		Query query = session.getCurrentSession()
				.getNamedQuery("Student.getByLevel").setInteger("level", level);

		@SuppressWarnings("unchecked")
		List<Student> results = query.list();
		return results;
	}

}
