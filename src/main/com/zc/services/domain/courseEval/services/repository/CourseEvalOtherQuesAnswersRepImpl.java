/**
 * 
 */
package main.com.zc.services.domain.courseEval.services.repository;

import java.util.List;

import main.com.zc.services.domain.courseEval.model.CourseEvalOtherQuesAnswers;
import main.com.zc.services.domain.courseEval.model.ICourseEvalOtherQuesAnswersRep;

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
@Repository("ICourseEvalOtherQuesAnswersRep")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CourseEvalOtherQuesAnswersRepImpl implements ICourseEvalOtherQuesAnswersRep{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	
	@Override
	public List<CourseEvalOtherQuesAnswers> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalOtherQuesAnswers.getAll");

			@SuppressWarnings("unchecked")
			List<CourseEvalOtherQuesAnswers> results = query.list();
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
	public List<CourseEvalOtherQuesAnswers> getByCategoryID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalOtherQuesAnswers.getByCategoryID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalOtherQuesAnswers> results = query.list();
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
	public List<CourseEvalOtherQuesAnswers> getByQuestID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalOtherQuesAnswers.getByQuestID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalOtherQuesAnswers> results = query.list();
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
	public List<CourseEvalOtherQuesAnswers> getByStudentID(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("CourseEvalOtherQuesAnswers.getByStudentID").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalOtherQuesAnswers> results = query.list();
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
	public CourseEvalOtherQuesAnswers getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("CourseEvalOtherQuesAnswers.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<CourseEvalOtherQuesAnswers> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public CourseEvalOtherQuesAnswers add(CourseEvalOtherQuesAnswers form) {
		try{
			CourseEvalOtherQuesAnswers ans=getByQuestIDAndStudentID(form.getQuestion().getId(), form.getStudent().getId());
			if(ans==null)
			{
			sessionFactory.getCurrentSession().save(form);
			return form;
			}
			else return null;
		}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public CourseEvalOtherQuesAnswers update(CourseEvalOtherQuesAnswers form) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(form);
			tx1.commit();
			session.close();
			return form;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(CourseEvalOtherQuesAnswers form) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(getById(form.getId()));
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public CourseEvalOtherQuesAnswers getByQuestIDAndStudentID(Integer id,
			Integer stID) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("CourseEvalOtherQuesAnswers.getByQuestIDAndStudentID").setInteger("id", id).setInteger("stID",stID);

			@SuppressWarnings("unchecked")
			List<CourseEvalOtherQuesAnswers> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

}
