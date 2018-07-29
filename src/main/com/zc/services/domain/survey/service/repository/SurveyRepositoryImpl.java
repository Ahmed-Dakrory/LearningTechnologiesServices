/**
 * 
 */
package main.com.zc.services.domain.survey.service.repository;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import main.com.zc.services.domain.survey.model.ISurveyRepository;
import main.com.zc.services.domain.survey.model.Survey;

/**
 * @author omnya
 *
 */
@Repository("ISurveyRepository")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SurveyRepositoryImpl implements ISurveyRepository{

	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	

	
	@Override
	public Survey add(Survey survey) {
		try{
			sessionFactory.getCurrentSession().save(survey);
			return survey;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public Survey update(Survey survey) {
		try{
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.update(survey);
			tx1.commit();
			session.close();
			return survey;
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Survey getById(Integer id) {
		try{
			Query query = sessionFactory.getCurrentSession()
					.getNamedQuery("Survey.getById").setInteger("id", id);

			@SuppressWarnings("unchecked")
			List<Survey> results = query.list();
			return results.get(0);
			}
			catch(Exception ex)
			{
				
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Survey> getAll() {
		try{
			Query query = sessionFactory.getCurrentSession().getNamedQuery("Survey.getAll");

			@SuppressWarnings("unchecked")
			List<Survey> results = query.list();
			return results;
			}
			catch(Exception ex)
			{
				System.out.println("Error in getting surveys ");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean delete(Survey survey) {
		try {
			session = sessionFactory.openSession();
			Transaction tx1 = session.beginTransaction();
			session.delete(survey);
			tx1.commit();
			session.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
