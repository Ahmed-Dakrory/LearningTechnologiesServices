/**
 * 
 */
package main.com.zc.services.domain.courseEval.services.repository;

import java.util.List;

import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.courseEval.model.ICourseEvalQuestionsRep;

import org.hibernate.HibernateException;
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
@Repository("ICourseEvalQuestionsRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseEvalQuestionsRepImpl implements ICourseEvalQuestionsRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	Transaction transaction = null;
	
	@Override
	public List<CourseEvalQuestions> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalQuestions.getAll");

			@SuppressWarnings("unchecked")
			List<CourseEvalQuestions> results = query.list();
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
	public List<CourseEvalQuestions> getByCategoryID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalQuestions.getByCategoryID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalQuestions> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("error in getting forms of student");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public CourseEvalQuestions getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("CourseEvalQuestions.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalQuestions> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public CourseEvalQuestions add(CourseEvalQuestions form) {
		try{
			session =sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			session.saveOrUpdate(form);
			transaction.commit();
			return form;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				transaction.rollback();
				return null;
			}
	 finally {
			session.close();
		}
	}

	@Override
	public CourseEvalQuestions update(CourseEvalQuestions form) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(form);
			tx1.commit();
			
			return form;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}finally {
			session.close();
		}
	}

	@Override
	public boolean delete(CourseEvalQuestions form) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			
			session.delete(getById(form.getId()));
			tx1.commit();
		//	session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		finally {
			session.close();
		}
	}

}
