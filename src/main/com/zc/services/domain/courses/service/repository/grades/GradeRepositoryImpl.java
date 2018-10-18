/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.grades;

import java.util.List;

import main.com.zc.services.domain.courses.model.grades.Grade;
import main.com.zc.services.domain.courses.model.grades.GradesRepository;

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
public class GradeRepositoryImpl implements GradesRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session; 
	

	@Override
	public List<Grade> getByCourseId(int id) {
		 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Grade.getByCourseId").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<Grade> results=query.list();
		   return results;
	}

	@Override
	public Grade addGrade(Grade grade) {
		try{
			System.out.println("Ahmed OKKKKKKKKKKKKKK");
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.saveOrUpdate(grade);
			tx1.commit();
			session.close(); 
			return grade; 
			}
			catch(Exception ex)
			{
				System.out.println(">>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Grade> getAll() {
				 Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Grade.getAll");

				 @SuppressWarnings("unchecked")
				List<Grade> results=query.list();
				   return results;
	}

	@Override
	public Grade getById(int id) {
		Query query 	=sessionFactory.getCurrentSession().getNamedQuery("Grade.getById").setInteger("id",id);

		 @SuppressWarnings("unchecked")
		List<Grade> results=query.list();
		   return results.get(0);
	}

	@Override
	public boolean delete(Grade grade) {
		// TODO Auto-generated method stub
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(grade);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	

}
